package ru.itis.dis403.lab2_2.orm;

import java.io.Closeable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntityManagerImpl implements EntityManager, Closeable {

    private Connection connection;
    private Map<Class<?>, EntityMetaData> metaDataMap;

    public EntityManagerImpl(Connection connection, Map<Class<?>, EntityMetaData> metaDataMap) {
        this.connection = connection;
        this.metaDataMap = metaDataMap;
    }

    @Override
    public <T> T save(T entity) throws IllegalAccessException, SQLException {
        Class<?> clazz = entity.getClass();
        EntityMetaData metaData = metaDataMap.get(clazz);

        Field idField = metaData.getIdField();
        idField.setAccessible(true);
        Object id = idField.get(entity);

        // INSERT
        if (id == null) {
            StringBuilder sql = new StringBuilder();
            sql.append("insert into ")
                    .append(metaData.getTableName())
                    .append("(");

            for (Field field : metaData.getColumns()) {
                sql.append(field.getName()).append(",");
            }
            for (Field field : metaData.getRelations()) {
                sql.append(field.getName()).append("_id,");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(") values (");

            int columnsCount = metaData.getColumns().size() + metaData.getRelations().size();
            for (int i = 0; i < columnsCount; i++) {
                sql.append("?,");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(")");

            try (PreparedStatement ps = connection.prepareStatement(sql.toString(),
                    Statement.RETURN_GENERATED_KEYS)) {

                int index = 1;
                // обычные поля
                for (Field field : metaData.getColumns()) {
                    field.setAccessible(true);
                    Object o = field.get(entity);
                    ps.setObject(index++, o);
                }
                // связи many-to-one: пишем id связанной сущности
                for (Field field : metaData.getRelations()) {
                    field.setAccessible(true);
                    Object o = field.get(entity);
                    if (o != null) {
                        EntityMetaData relData = metaDataMap.get(field.getType());
                        Field idRelField = relData.getIdField();
                        idRelField.setAccessible(true);
                        Object idRel = idRelField.get(o);
                        ps.setObject(index++, idRel);
                    } else {
                        ps.setObject(index++, null);
                    }
                }

                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    Object generatedId = rs.getObject(1);
                    idField.set(entity, ((Number) generatedId).longValue());
                }
            }
        }
        // UPDATE
        else {
            StringBuilder sql = new StringBuilder();
            sql.append("update ")
                    .append(metaData.getTableName())
                    .append(" set ");

            for (Field field : metaData.getColumns()) {
                sql.append(field.getName()).append("=?,");
            }
            for (Field field : metaData.getRelations()) {
                sql.append(field.getName()).append("_id=?,");
            }
            sql.deleteCharAt(sql.length() - 1);
            sql.append(" where ")
                    .append(metaData.getIdField().getName())
                    .append("=?");

            try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
                int index = 1;
                // обычные поля
                for (Field field : metaData.getColumns()) {
                    field.setAccessible(true);
                    Object o = field.get(entity);
                    ps.setObject(index++, o);
                }
                // связи
                for (Field field : metaData.getRelations()) {
                    field.setAccessible(true);
                    Object o = field.get(entity);
                    if (o != null) {
                        EntityMetaData relData = metaDataMap.get(field.getType());
                        Field idRelField = relData.getIdField();
                        idRelField.setAccessible(true);
                        Object idRel = idRelField.get(o);
                        ps.setObject(index++, idRel);
                    } else {
                        ps.setObject(index++, null);
                    }
                }
                ps.setObject(index, id);
                ps.executeUpdate();
            }
        }

        return entity;
    }

    @Override
    public void remove(Object entity) throws IllegalAccessException, SQLException {
        Class<?> clazz = entity.getClass();
        EntityMetaData metaData = metaDataMap.get(clazz);
        Field idField = metaData.getIdField();
        idField.setAccessible(true);
        Object id = idField.get(entity);

        String sql = "delete from " + metaData.getTableName() +
                " where " + idField.getName() + "=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public <T> T find(Class<T> entityType, Object key)
            throws SQLException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {

        EntityMetaData metaData = metaDataMap.get(entityType);

        String sql = "select * from " + metaData.getTableName() +
                " where " + metaData.getIdField().getName() + "=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, key);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                T entity = entityType.getDeclaredConstructor().newInstance();

                // id
                Field idField = metaData.getIdField();
                idField.setAccessible(true);
                Object id = rs.getObject(idField.getName());
                idField.set(entity, id);

                // обычные поля
                for (Field field : metaData.getColumns()) {
                    field.setAccessible(true);
                    Object value = rs.getObject(field.getName());
                    field.set(entity, value);
                }

                // связи many-to-one: пока можно пропустить или реализовать позже
                // (одногруппник чаще всего не загружает связанные сущности в этом методе)

                return entity;
            }
        }
    }

    @Override
    public <T> List<T> findAll(Class<T> entityType)
            throws SQLException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {

        EntityMetaData metaData = metaDataMap.get(entityType);
        String sql = "select * from " + metaData.getTableName();

        List<T> result = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                T entity = entityType.getDeclaredConstructor().newInstance();

                Field idField = metaData.getIdField();
                idField.setAccessible(true);
                Object id = rs.getObject(idField.getName());
                idField.set(entity, id);

                for (Field field : metaData.getColumns()) {
                    field.setAccessible(true);
                    Object value = rs.getObject(field.getName());
                    field.set(entity, value);
                }

                result.add(entity);
            }
        }

        return result;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
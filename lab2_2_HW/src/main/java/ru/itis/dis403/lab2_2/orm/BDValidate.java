package ru.itis.dis403.lab2_2.orm;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class BDValidate {

    public static void validate(EntityMetaData metaData, Connection connection) throws SQLException {
        Set<String> tables = getTables(connection);
        if (!tables.contains(metaData.getTableName())) {
            throw new RuntimeException("таблица " + metaData.getTableName() + " не найдена");
        }

        Set<String> columns = getColumns(connection, metaData.getTableName());

        String id = metaData.getIdField().getName();
        if (!columns.contains(id)) {
            throw new RuntimeException("у сущности нет id");
        }

        for (Field field : metaData.getColumns()) {
            if (!columns.contains(field.getName())) {
                throw new RuntimeException("у сущности нет поля: " + field.getName());
            }
        }

        for (Field field : metaData.getRelations()) {
            String column = field.getName() + "_id";
            if (!columns.contains(column)) {
                throw new RuntimeException("у сущности нет фк: " + column);
            }
        }
    }

    public static Set<String> getTables(Connection connection) throws SQLException {
        Set<String> tables = new HashSet<>();

        String sql = """
                SELECT
                    table_name
                FROM
                    information_schema.tables
                WHERE
                    table_type = 'BASE TABLE'
                    AND table_schema NOT IN ('pg_catalog', 'information_schema');
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                tables.add(rs.getString("table_name"));
            }
        }

        return tables;
    }

    public static Set<String> getColumns(Connection connection, String tableName) throws SQLException {
        HashSet<String> columns = new HashSet<>();

        String sql = """
                SELECT a.attname
                FROM pg_catalog.pg_attribute a
                WHERE a.attrelid = (
                    SELECT c.oid FROM pg_catalog.pg_class c
                    LEFT JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace
                    WHERE pg_catalog.pg_table_is_visible(c.oid)
                      AND c.relname = ?
                )
                  AND a.attnum > 0 AND NOT a.attisdropped
                """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, tableName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    columns.add(rs.getString("attname"));
                }
            }
        }

        return columns;
    }
}

package ru.itis.dis403.lab2_2.orm;

import ru.itis.dis403.lab2_2.orm.annotation.Column;
import ru.itis.dis403.lab2_2.orm.annotation.Entity;
import ru.itis.dis403.lab2_2.orm.annotation.Id;
import ru.itis.dis403.lab2_2.orm.annotation.ManyToOne;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EntityMetaData {

    private String tableName;
    private Field idField;
    private List<Field> columns = new ArrayList<>();
    private List<Field> relations = new ArrayList<>();

    public String getTableName() { return tableName; }
    public Field getIdField() { return idField; }
    public List<Field> getColumns() { return columns; }
    public List<Field> getRelations() { return relations; }

    public static EntityMetaData parseEntity(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Класс не является сущностью: " + clazz.getName());
        }

        EntityMetaData metaData = new EntityMetaData();
        metaData.tableName = clazz.getSimpleName().toLowerCase();

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                metaData.idField = field;
            } else if (field.isAnnotationPresent(Column.class)) {
                metaData.columns.add(field);
            } else if (field.isAnnotationPresent(ManyToOne.class)) {
                metaData.relations.add(field);
            }
        }

        if (metaData.idField == null) {
            throw new RuntimeException("У сущности " + clazz.getName() + " нет @Id");
        }

        return metaData;
    }
}

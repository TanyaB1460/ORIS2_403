package ru.itis.dis403.lab2_2.orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface EntityManager {

    <T> T save(T entity) throws IllegalAccessException, SQLException;

    void remove(Object entity) throws IllegalAccessException, SQLException;

    <T> T find(Class<T> entityType, Object key)
            throws SQLException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException;

    <T> List<T> findAll(Class<T> entityType)
            throws SQLException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException;
}

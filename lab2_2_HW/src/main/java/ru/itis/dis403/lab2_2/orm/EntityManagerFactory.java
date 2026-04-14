package ru.itis.dis403.lab2_2.orm;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.dis403.lab2_2.orm.scan.PathScan;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityManagerFactory {

    private String packagePath = "ru.itis.dis403.lab2_2.orm.model";

    private List<Class<?>> entities;
    private Map<Class<?>, EntityMetaData> metaDataMap = new HashMap<>();
    private HikariDataSource dataSource;

    public EntityManagerFactory() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/lab2_2");
        config.setUsername("postgres");
        config.setPassword("2685");
        config.setConnectionTimeout(50000);
        config.setMaximumPoolSize(10);
        dataSource = new HikariDataSource(config);

        entities = PathScan.find(packagePath);

        try (Connection connection = dataSource.getConnection()) {
            for (Class<?> clazz : entities) {
                EntityMetaData metaData = EntityMetaData.parseEntity(clazz);
                BDValidate.validate(metaData, connection);
                metaDataMap.put(clazz, metaData);
            }
        }
    }

    public EntityManager getEntityManager() {
        try {
            return new EntityManagerImpl(dataSource.getConnection(), metaDataMap);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        dataSource.close();
    }
}

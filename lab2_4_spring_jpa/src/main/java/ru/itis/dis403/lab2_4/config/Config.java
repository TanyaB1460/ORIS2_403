package ru.itis.dis403.lab2_4.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;
import java.util.Properties;

/*
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("ru.itis.dis403.lab2_4.repository")
@ComponentScan("ru.itis.dis403.lab2_4")
*/
public class Config {

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/lab2_4");
        config.setUsername("postgres");
        config.setPassword("2685");
        config.setDriverClassName("org.postgresql.Driver");
        return config;
    }

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        vendorAdapter.setGenerateDdl(true); //авто генерация таблиц

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("ru.itis.dis403.lab2_4.model"); //откуда сущности
        factory.setDataSource(dataSource()); //подключение к БД
        factory.setJpaProperties(additionalProperties()); //доп настройки
        return factory;
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update"); //создавай если нет
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect"); //специфические типы данных
        properties.setProperty("hibernate.show_sql", "true"); //покказывать что присходит
        return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}
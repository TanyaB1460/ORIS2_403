package ru.itis.dis403.lab2_2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("ru.itis.dis403.lab2_2")
public class Config {

    @Bean
    public String appName() {
        return "Встроенный Tomcat и Spring Context";
    }
}
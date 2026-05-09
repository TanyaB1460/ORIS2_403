package ru.itis.dis403.lab2_6.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.itis.dis403.lab2_6.model.BookingsData;
import ru.itis.dis403.lab2_6.model.Hotel;
import ru.itis.dis403.lab2_6.repository.BookingRepository;
import ru.itis.dis403.lab2_6.repository.HotelRepository;

import java.io.File;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ru.itis.dis403.lab2_6.repository")
public class ApplicationConfig {
}
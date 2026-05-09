package ru.itis.dis403.lab2_8.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Subscription;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;
import ru.itis.dis403.lab2_8.model.Weather;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class WeatherService {
    private Connection natsConnection;
    private Subscription subscription;
    private Weather cachedWeather;
    private final ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        try {
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            String natsUrl = System.getenv().getOrDefault("NATS_URL", "nats://localhost:4222");
            natsConnection = Nats.connect(natsUrl);
            subscription = natsConnection.subscribe("Weather");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public Weather getWeather() {
        try {
            if (subscription != null) {
                var msg = subscription.nextMessage(Duration.ofMillis(500));
                if (msg != null) {
                    cachedWeather = mapper.readValue(msg.getData(), Weather.class);
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        if (cachedWeather == null) {
            cachedWeather = createDefaultWeather();
        }
        return cachedWeather;
    }

    private Weather createDefaultWeather() {
        Weather weather = new Weather();
        weather.setCity("Казань");
        weather.setTemp(0.0);
        weather.setPressure(760.0);
        weather.setWindSpeed(0.0);
        weather.setWindDirection("N");
        weather.setDateTime(LocalDateTime.now());
        return weather;
    }

    @PreDestroy
    public void cleanup() {
        try {
            if (natsConnection != null) {
                natsConnection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
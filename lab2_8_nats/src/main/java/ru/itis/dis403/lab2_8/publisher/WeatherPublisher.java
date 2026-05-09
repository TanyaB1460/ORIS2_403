package ru.itis.dis403.lab2_8.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.nats.client.Connection;
import io.nats.client.Nats;
import ru.itis.dis403.lab2_8.model.Weather;
import java.time.LocalDateTime;
import java.util.Random;

public class WeatherPublisher {
    public static void main(String[] args) {
        String subject = "Weather";
        String natsUrl = System.getenv().getOrDefault("NATS_URL", "nats://localhost:4222");

        try (Connection nc = Nats.connect(natsUrl)) {
            Random random = new Random();

            while (true) {
                Weather weather = new Weather();
                weather.setCity("Казань");
                weather.setTemp(10.0 + random.nextDouble() * 2 - 1);
                weather.setPressure(744 + random.nextDouble() * 4 - 2);
                weather.setWindSpeed(3 + random.nextDouble() * 4 - 2);
                weather.setWindDirection("СЗ");
                weather.setDateTime(LocalDateTime.now());

                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());

                byte[] msg = mapper.writeValueAsBytes(weather);

                nc.publish(subject, msg);

                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
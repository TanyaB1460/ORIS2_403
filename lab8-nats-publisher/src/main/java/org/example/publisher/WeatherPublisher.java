package org.example.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import io.nats.client.Nats;
import org.example.model.Weather;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDateTime;
import java.util.Random;

public class WeatherPublisher {

    public static void main(String[] args) {

        String subject = "Weather";

        try (Connection nc = Nats.connect("nats://nats:4222")) {

            while (true) {

                Random random = new Random();
                Weather weather = Weather.builder()
                        .city("Казань")
                        .temp(10. + random.nextDouble() * 2 - 1)
                        .pressure(744 + random.nextDouble() * 4 - 2)
                        .windSpeed(3 + random.nextDouble() * 4 - 2)
                        .dateTime(LocalDateTime.now())
                        .windDirection("СЗ").build();

                ObjectMapper mapper = Jackson2ObjectMapperBuilder.json().build();
                byte[] msg = mapper.writeValueAsBytes(weather);

                // Отправка сообщения
                nc.publish(subject, msg);

                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
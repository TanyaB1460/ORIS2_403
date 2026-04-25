package org.example.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Data
@Builder
@Getter@Setter
public class Weather {

    private String city;
    private Double temp;
    private Double pressure;
    private Double windSpeed;
    private String windDirection;

    private LocalDateTime dateTime;



}
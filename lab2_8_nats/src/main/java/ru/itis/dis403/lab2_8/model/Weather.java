package ru.itis.dis403.lab2_8.model;

import java.time.LocalDateTime;

public class Weather {
    private String city;
    private Double temp;
    private Double pressure;
    private Double windSpeed;
    private String windDirection;
    private LocalDateTime dateTime;

    public Weather() {}

    public Weather(String city, Double temp, Double pressure,
                   Double windSpeed, String windDirection, LocalDateTime dateTime) {
        this.city = city;
        this.temp = temp;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.dateTime = dateTime;
    }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Double getTemp() { return temp; }
    public void setTemp(Double temp) { this.temp = temp; }

    public Double getPressure() { return pressure; }
    public void setPressure(Double pressure) { this.pressure = pressure; }

    public Double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(Double windSpeed) { this.windSpeed = windSpeed; }

    public String getWindDirection() { return windDirection; }
    public void setWindDirection(String windDirection) { this.windDirection = windDirection; }

    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
}
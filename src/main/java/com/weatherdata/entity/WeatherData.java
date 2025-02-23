package com.weatherdata.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "weather_data")
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String country;
    private double temperature;
    private int humidity;
    private double windSpeed;
    private int pressure;
    private String weatherDesc;

    @Column(nullable = false, updatable = false)
    private LocalDateTime recordedAt;

    @PrePersist
    protected void onCreate() {
        recordedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public int getHumidity() { return humidity; }
    public void setHumidity(int humidity) { this.humidity = humidity; }

    public double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }

    public int getPressure() { return pressure; }
    public void setPressure(int pressure) { this.pressure = pressure; }

    public String getWeatherDesc() { return weatherDesc; }
    public void setWeatherDesc(String weatherDesc) { this.weatherDesc = weatherDesc; }

    public LocalDateTime getRecordedAt() { return recordedAt; }
}

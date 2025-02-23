package com.weatherdata.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherApiResponse {
    private Main main;
    private Wind wind;
    private List<Weather> weather;
    private Sys sys;


    // Inner static classes for API fields
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Main {
        private double temp;
        private int pressure;
        private int humidity;

        public double getTemp() { return temp; }
        public void setTemp(double temp) { this.temp = temp; }

        public int getPressure() { return pressure; }
        public void setPressure(int pressure) { this.pressure = pressure; }

        public int getHumidity() { return humidity; }
        public void setHumidity(int humidity) { this.humidity = humidity; }
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Wind {
        private double speed;

        public double getSpeed() { return speed; }
        public void setSpeed(double speed) { this.speed = speed; }
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Weather {
        private String description;

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Sys {
        private String country;

        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
    }
}

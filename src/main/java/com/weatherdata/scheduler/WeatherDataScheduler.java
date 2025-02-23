package com.weatherdata.scheduler;

import com.weatherdata.service.WeatherDataService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeatherDataScheduler {

    private final WeatherDataService weatherDataService;

    // Define a list of cities to track weather for
    private static final List<String> CITIES = List.of("New York", "London", "Mumbai", "Tokyo");

    public WeatherDataScheduler(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    // Run every hour (3600000 ms = 1 hour)
    @Scheduled(fixedRate = 3600000)
    public void fetchWeatherDataForCities() {
        for (String city : CITIES) {
            weatherDataService.fetchAndSaveWeatherData(city);
            System.out.println("Fetched weather data for: " + city);
        }
    }
}


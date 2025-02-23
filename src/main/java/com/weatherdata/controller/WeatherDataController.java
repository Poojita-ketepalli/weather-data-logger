package com.weatherdata.controller;

import com.weatherdata.entity.WeatherData;
import com.weatherdata.service.WeatherDataService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
public class WeatherDataController {

    private final WeatherDataService weatherDataService;

    public WeatherDataController(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    // 🌤 Get the latest weather data for a city
    @GetMapping("/latest")
    public WeatherData getLatestWeather(@RequestParam String city) {
        return weatherDataService.getLatestWeather(city);
    }

    // 📜 Get weather history for a city
    @GetMapping("/history")
    public List<WeatherData> getWeatherHistory(@RequestParam String city) {
        return weatherDataService.getWeatherHistory(city);
    }

    // 📡 Manually trigger weather data fetch for a city
    @PostMapping("/fetch")
    public WeatherData fetchWeatherData(@RequestParam String city) {
        return weatherDataService.fetchAndSaveWeatherData(city);
    }
}


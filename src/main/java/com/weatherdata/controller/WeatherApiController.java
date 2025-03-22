package com.weatherdata.controller;

import com.weatherdata.entity.WeatherData;
import com.weatherdata.service.WeatherDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weather") // 🌐 Base API path
public class WeatherApiController {

    private final WeatherDataService weatherDataService;

    public WeatherApiController(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    // ✅ Get weather data for a city (API)
    @GetMapping("/{city}")
    public ResponseEntity<WeatherData> getWeatherByCity(@PathVariable String city) {
        WeatherData latestWeather = weatherDataService.fetchAndSaveWeatherData(city);

        if (latestWeather == null) {
            return ResponseEntity.notFound().build(); // Returns 404 if city data is missing
        }

        return ResponseEntity.ok(latestWeather);
    }

    // ✅ Get weather history for a city (API)
    @GetMapping("/{city}/history")
    public ResponseEntity<List<WeatherData>> getWeatherHistory(@PathVariable String city) {
        List<WeatherData> history = weatherDataService.getWeatherHistory(city);

        if (history.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(history);
    }
}


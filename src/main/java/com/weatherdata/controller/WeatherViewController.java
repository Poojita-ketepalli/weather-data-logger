package com.weatherdata.controller;

import com.weatherdata.entity.WeatherData;
import com.weatherdata.service.WeatherDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WeatherViewController {

    private final WeatherDataService weatherDataService;

    public WeatherViewController(WeatherDataService weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    // 🏠 Home Page - City search form
    @GetMapping("/")
    public String home() {
        return "index"; // ✅ Ensure "index.html" exists in /templates
    }

    // 📡 Fetch and display weather details for a city
    @PostMapping("/weather")
    public String getWeather(@RequestParam String city, Model model) {
        WeatherData latestWeather = weatherDataService.fetchAndSaveWeatherData(city); // ✅ Fetch new weather data

        if (latestWeather == null) {
            model.addAttribute("error", "Could not fetch weather data. Please try again.");
            return "index"; // ❌ Prevents Whitelabel error if API call fails
        }

        List<WeatherData> history = weatherDataService.getWeatherHistory(city);

        model.addAttribute("latestWeather", latestWeather);
        model.addAttribute("history", history);
        model.addAttribute("city", city);

        return "weather"; // ✅ Ensure "weather.html" exists in /templates
    }

    // Fetch all weather history
    @GetMapping("/history")
    public String getAllWeatherHistory(Model model) {
        List<WeatherData> history = weatherDataService.getAllWeatherHistory();
        model.addAttribute("history", history);
        return "weather-history"; // Assuming you have a Thymeleaf template named weather-history.html
    }

    // Delete a specific weather entry
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteWeatherData(@PathVariable Long id) {
        try {
            weatherDataService.deleteWeatherRecord(id);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed");
        }
    }

}

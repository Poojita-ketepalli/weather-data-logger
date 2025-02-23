package com.weatherdata.controller;

import com.weatherdata.entity.WeatherData;
import com.weatherdata.service.WeatherDataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}

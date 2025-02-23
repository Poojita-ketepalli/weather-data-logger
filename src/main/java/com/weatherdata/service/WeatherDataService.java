package com.weatherdata.service;

import com.weatherdata.dto.WeatherApiResponse;
import com.weatherdata.entity.WeatherData;
import com.weatherdata.repository.WeatherDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class WeatherDataService {

    private final WeatherDataRepository weatherDataRepository;
    private final RestTemplate restTemplate;

    // 🌎 OpenWeather API URL & Key (Replace with a real API key)
    private static final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";
    private static final String API_KEY = "c734a96c655305a661224cec85c0afbf"; // 🔑 Replace with your actual API key

    public WeatherDataService(WeatherDataRepository weatherDataRepository, RestTemplate restTemplate) {
        this.weatherDataRepository = weatherDataRepository;
        this.restTemplate = restTemplate;
    }

    // 📡 Fetch weather data from an API & store in DB
    public WeatherData fetchAndSaveWeatherData(String city) {
        String url = String.format(WEATHER_API_URL, city, API_KEY);

        try {
            WeatherApiResponse response = restTemplate.getForObject(url, WeatherApiResponse.class);

            if (response != null && response.getWeather() != null && !response.getWeather().isEmpty()) {
                WeatherData weatherData = new WeatherData();
                weatherData.setCity(city);
                weatherData.setCountry(response.getSys().getCountry());
                weatherData.setTemperature(response.getMain().getTemp());
                weatherData.setHumidity(response.getMain().getHumidity());
                weatherData.setWindSpeed(response.getWind().getSpeed());
                weatherData.setPressure(response.getMain().getPressure());
                weatherData.setWeatherDesc(response.getWeather().get(0).getDescription());

                return weatherDataRepository.save(weatherData);
            }
        } catch (HttpClientErrorException e) {
            System.out.println("❌ Error fetching weather data: " + e.getMessage());
        }

        return null;
    }

    // 📊 Get weather history for a city
    public List<WeatherData> getWeatherHistory(String city) {
        return weatherDataRepository.findByCity(city);
    }

    // ⏳ Get latest weather entry for a city
    public WeatherData getLatestWeather(String city) {
        return weatherDataRepository.findTopByCityOrderByRecordedAtDesc(city);
    }

    // Fetch all history
    public List<WeatherData> getAllWeatherHistory() {
        return weatherDataRepository.findAll();
    }

    // Delete a weather record by ID
    public boolean deleteWeatherRecord(Long id) {
        Optional<WeatherData> record = weatherDataRepository.findById(id);
        if (record.isPresent()) {
            weatherDataRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

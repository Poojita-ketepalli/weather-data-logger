package com.weatherdata.junittests.controller;

import com.weatherdata.controller.WeatherViewController;
import com.weatherdata.entity.WeatherData;
import com.weatherdata.repository.WeatherDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class WeatherViewControllerTest {

    @Autowired
    private WeatherViewController weatherViewController;  // ✅ Use actual instance

    @Mock
    private WeatherDataRepository weatherDataRepository; // ✅ Mock only dependencies

    @Test
    void testWeatherPage_ShouldReturnValidModelAttributes() {
        // Mock weather data
        WeatherData mockWeatherData = new WeatherData();
        mockWeatherData.setCity("Mumbai");
        mockWeatherData.setTemperature(28.0);
        mockWeatherData.setWeatherDesc("Sunny");

        // ✅ Mock repository methods
        Mockito.when(weatherDataRepository.findTopByCityOrderByRecordedAtDesc(Mockito.anyString())).thenReturn(mockWeatherData);
        Mockito.when(weatherDataRepository.findByCity(Mockito.anyString())).thenReturn(List.of(mockWeatherData));

        // Perform test
        Model model = new ExtendedModelMap();
        String viewName = weatherViewController.getWeather("Mumbai", model);

        // Assertions
        Assertions.assertEquals("weather", viewName);
        Assertions.assertNotNull(model.getAttribute("latestWeather"));
        Assertions.assertNotNull(model.getAttribute("history"));
        Assertions.assertEquals("Mumbai", model.getAttribute("city"));
    }
}

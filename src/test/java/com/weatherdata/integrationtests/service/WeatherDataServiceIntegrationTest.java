package com.weatherdata.integrationtests.service;

import com.weatherdata.entity.WeatherData;
import com.weatherdata.repository.WeatherDataRepository;
import com.weatherdata.service.WeatherDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WeatherDataServiceIntegrationTest {

    @Autowired
    private WeatherDataService weatherDataService;

    @MockBean
    private WeatherDataRepository weatherDataRepository;

    @BeforeEach
    void setup() {
        WeatherData weather = new WeatherData();
        weather.setCity("London");
        weather.setTemperature(20.0);
        weather.setHumidity(60);
        weather.setWindSpeed(5.0);
        weather.setPressure(1012);
        weather.setWeatherDesc("Clear Sky");

        when(weatherDataRepository.findTopByCityOrderByRecordedAtDesc("London"))
                .thenReturn(weather);
    }

    @Test
    void testGetLatestWeather() {
        WeatherData weather = weatherDataService.getLatestWeather("London");

        assertThat(weather).isNotNull();
        assertThat(weather.getCity()).isEqualTo("London");
        assertThat(weather.getTemperature()).isEqualTo(20.0);
        assertThat(weather.getWeatherDesc()).isEqualTo("Clear Sky");
    }
}

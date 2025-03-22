package com.weatherdata.junittests.service;

import com.weatherdata.dto.WeatherApiResponse;
import com.weatherdata.entity.WeatherData;
import com.weatherdata.repository.WeatherDataRepository;
import com.weatherdata.service.WeatherDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class WeatherDataServiceTest {

    @Mock
    private WeatherDataRepository weatherDataRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherDataService weatherDataService;

    @BeforeEach
    void setup() {
        // Reset mocks before each test
        reset(weatherDataRepository, restTemplate);
    }

    @Test
    void fetchAndSaveWeatherData_ShouldReturnWeatherData_WhenApiReturnsValidResponse() {
        // Mock API Response
        WeatherApiResponse mockResponse = new WeatherApiResponse();
        mockResponse.setMain(new WeatherApiResponse.Main(30.0, 1008, 65));
        mockResponse.setWind(new WeatherApiResponse.Wind(10.5));
        mockResponse.setSys(new WeatherApiResponse.Sys("IN"));
        mockResponse.setWeather(List.of(new WeatherApiResponse.Weather("Cloudy")));

        when(restTemplate.getForObject(anyString(), eq(WeatherApiResponse.class))).thenReturn(mockResponse);

        // Mock repository save behavior to return the saved entity
        when(weatherDataRepository.save(any(WeatherData.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        WeatherData savedWeather = weatherDataService.fetchAndSaveWeatherData("Delhi");

        assertNotNull(savedWeather);
        assertEquals("Delhi", savedWeather.getCity());
        assertEquals(30.0, savedWeather.getTemperature());
        assertEquals("Cloudy", savedWeather.getWeatherDesc());

        verify(weatherDataRepository, times(1)).save(any(WeatherData.class));
    }


    @Test
    void getWeatherHistory_ShouldReturnList_WhenCityExists() {
        WeatherData mockData = new WeatherData();
        mockData.setCity("Mumbai");
        mockData.setTemperature(28.0);

        when(weatherDataRepository.findByCity("Mumbai")).thenReturn(List.of(mockData));

        List<WeatherData> result = weatherDataService.getWeatherHistory("Mumbai");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Mumbai", result.get(0).getCity());
    }
}

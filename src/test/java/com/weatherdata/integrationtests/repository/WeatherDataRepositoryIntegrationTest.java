package com.weatherdata.integrationtests.repository;


import com.weatherdata.entity.WeatherData;
import com.weatherdata.repository.WeatherDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WeatherDataRepositoryIntegrationTest {

    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @Test
    public void testSaveWeatherData() {
        WeatherData data = new WeatherData();
        data.setCity("New York");
        data.setTemperature(22.5);

        WeatherData savedData = weatherDataRepository.save(data);
        Optional<WeatherData> retrievedData = weatherDataRepository.findById(savedData.getId());

        Assertions.assertTrue(retrievedData.isPresent());
        Assertions.assertEquals("New York", retrievedData.get().getCity());
    }
}


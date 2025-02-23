package com.weatherdata.repository;

import com.weatherdata.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    // Find weather data for a specific city
    List<WeatherData> findByCity(String city);

    // Find weather data recorded after a certain timestamp
    List<WeatherData> findByRecordedAtAfter(LocalDateTime timestamp);

    // Find the latest weather data for a city
    WeatherData findTopByCityOrderByRecordedAtDesc(String city);
}

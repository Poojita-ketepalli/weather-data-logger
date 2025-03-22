package com.weatherdata.integrationtests.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WeatherControllerIntegrationTesting {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetWeatherByCity() throws Exception {
        mockMvc.perform(get("/api/weather/London"))
                .andExpect(status().isOk())  // ✅ Should return 200 OK
                .andExpect(jsonPath("$.city").value("London"))
                .andExpect(jsonPath("$.temperature").exists());
    }

    @Test
    public void testGetWeatherHistory() throws Exception {
        mockMvc.perform(get("/api/weather/London/history"))
                .andExpect(status().isOk())  // ✅ Should return 200 OK
                .andExpect(jsonPath("$").isArray());  // ✅ Ensures response is a JSON array
    }
}

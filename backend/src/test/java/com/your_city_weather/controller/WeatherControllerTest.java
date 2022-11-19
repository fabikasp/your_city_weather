package com.your_city_weather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.your_city_weather.model.WeatherReport;
import com.your_city_weather.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerTest {

    @MockBean
    private WeatherService weatherService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private WeatherReport weatherReport;

    private WeatherReport[] weatherReports;

    @BeforeEach
    void setUp() {
        WeatherReport firstWeatherReport = new WeatherReport(
            "Clear",
            "Klarer Himmel",
            "01d",
            1.3,
            -1.29,
            1.3,
            1013.0,
            62.0,
            10000,
            1.68,
            null,
            null,
            0.0,
            new Date()
        );
        WeatherReport secondWeatherReport = new WeatherReport(
            "Clouds",
            "Mäßig bewölkt",
            "03n",
            -1.36,
            -2.69,
            -1.36,
            1014.0,
            68.0,
            10000,
            2.62,
            3.0,
            4.6,
            33.0,
            new Date()
        );
        weatherReport = firstWeatherReport;
        weatherReports = new WeatherReport[] { firstWeatherReport, secondWeatherReport };
    }

    @Test
    void testCurrentWeatherNotFound() throws Exception {
        Mockito.when(weatherService.getCurrentWeatherForCity("DE", "NichtExistierendeStadt"))
            .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        mvc.perform(get("/current-weather/DE/NichtExistierendeStadt")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testCurrentWeatherInternalServerError() throws Exception {
        Mockito.when(weatherService.getCurrentWeatherForCity("DE", "Berlin"))
            .thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        mvc.perform(get("/current-weather/DE/Berlin")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void testCurrentWeatherSuccess() throws Exception {
        Mockito.when(weatherService.getCurrentWeatherForCity("DE", "Berlin")).thenReturn(weatherReport);
        final String expectedResponse = objectMapper.writeValueAsString(weatherReport);
        mvc.perform(get("/current-weather/DE/Berlin")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(expectedResponse));
    }

    @Test
    void testWeatherForecastNotFound() throws Exception {
        Mockito.when(
            weatherService.getWeatherForecastForCity(0, "DE", "NichtExistierendeStadt")
        ).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        mvc.perform(get("/weather-forecast/0/DE/NichtExistierendeStadt")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testWeatherForecastInternalServerError() throws Exception {
        Mockito.when(weatherService.getWeatherForecastForCity(0, "DE", "Berlin"))
            .thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        mvc.perform(get("/weather-forecast/0/DE/Berlin")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void testWeatherForecastSuccess() throws Exception {
        Mockito.when(weatherService.getWeatherForecastForCity(0, "DE", "Berlin"))
            .thenReturn(weatherReports);
        final String expectedResponse = objectMapper.writeValueAsString(weatherReports);
        mvc.perform(get("/weather-forecast/0/DE/Berlin")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(expectedResponse));
    }
}

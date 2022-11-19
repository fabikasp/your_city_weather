package com.your_city_weather.service;

import com.your_city_weather.api.WeatherApi;
import com.your_city_weather.api.WeatherForecastResponse;
import com.your_city_weather.api.WeatherReportResponse;
import com.your_city_weather.model.WeatherReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WeatherServiceTest {

    @Autowired
    private WeatherService weatherService;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private WeatherApi weatherApi;

    private WeatherReportResponse weatherReportResponse;

    private WeatherForecastResponse weatherForecastResponse;

    private WeatherReport weatherReport;

    private WeatherReport[] weatherReports;

    @BeforeEach
    void setUp() {
        Integer weatherReportTimestamp = 1668872761;
        Date weatherReportDate = new Date((long) weatherReportTimestamp * 1000);
        LinkedHashMap<String, String> weatherMap = new LinkedHashMap<>();
        weatherMap.put("main", "Clear");
        weatherMap.put("description", "Klarer Himmel");
        weatherMap.put("icon", "01d");
        LinkedHashMap<String, Double> mainMap = new LinkedHashMap<>();
        mainMap.put("temp", 1.3);
        mainMap.put("temp_min", -1.29);
        mainMap.put("temp_max", 1.3);
        mainMap.put("pressure", 1013.0);
        mainMap.put("humidity", 62.0);
        LinkedHashMap<String, Double> windMap = new LinkedHashMap<>();
        windMap.put("speed", 1.68);
        LinkedHashMap<String, Double> cloudMap = new LinkedHashMap<>();
        cloudMap.put("all", 0.0);
        weatherReportResponse = new WeatherReportResponse(
            new LinkedHashMap[] { weatherMap },
            mainMap,
            10000,
            windMap,
            null,
            cloudMap,
            weatherReportTimestamp
        );
        weatherForecastResponse = new WeatherForecastResponse(new WeatherReportResponse[] { weatherReportResponse });
        weatherReport = new WeatherReport(
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
            weatherReportDate
        );
        weatherReports = new WeatherReport[] { weatherReport };
        Mockito.when(weatherApi.buildCurrentWeatherUrl("DE", "Berlin"))
            .thenReturn("testCurrentWeatherUrl");
        Mockito.when(weatherApi.buildWeatherForecastUrl("DE", "Berlin"))
            .thenReturn("testWeatherForecastUrl");
    }

    @Test()
    void getCurrentWeatherForCityError() {
        Mockito.when(restTemplate.getForEntity("testCurrentWeatherUrl", WeatherReportResponse.class))
            .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        ResponseStatusException responseStatusException = assertThrows(
            ResponseStatusException.class,
            () -> weatherService.getCurrentWeatherForCity("DE", "Berlin")
        );
        assertSame(responseStatusException.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    void getCurrentWeatherForCitySuccess() {
        ResponseEntity<WeatherReportResponse> responseEntity = new ResponseEntity<>(
            weatherReportResponse,
            new HttpHeaders(),
            HttpStatus.OK
        );
        Mockito.when(restTemplate.getForEntity("testCurrentWeatherUrl", WeatherReportResponse.class))
            .thenReturn(responseEntity);
        assertThat(weatherService.getCurrentWeatherForCity("DE", "Berlin")).isEqualTo(weatherReport);
    }

    @Test
    void getWeatherForecastForCityError() {
        Mockito.when(restTemplate.getForEntity("testWeatherForecastUrl", WeatherForecastResponse.class))
            .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseStatusException responseStatusException = assertThrows(
            ResponseStatusException.class,
            () -> weatherService.getWeatherForecastForCity(0, "DE", "Berlin")
        );
        assertSame(responseStatusException.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void getWeatherForecastForCitySuccess() {
        ResponseEntity<WeatherForecastResponse> responseEntity = new ResponseEntity<>(
            weatherForecastResponse,
            new HttpHeaders(),
            HttpStatus.OK
        );
        Mockito.when(restTemplate.getForEntity("testWeatherForecastUrl", WeatherForecastResponse.class))
            .thenReturn(responseEntity);
        assertThat(weatherService.getWeatherForecastForCity(0, "DE", "Berlin"))
            .isEqualTo(weatherReports);
    }
}

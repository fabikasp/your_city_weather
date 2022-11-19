package com.your_city_weather.service;

import com.your_city_weather.api.WeatherApi;
import com.your_city_weather.api.WeatherForecastResponse;
import com.your_city_weather.api.WeatherReportResponse;
import com.your_city_weather.model.WeatherReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherApi weatherApi;

    public WeatherReport getCurrentWeatherForCity(String countryCode, String city) {
        ResponseEntity<WeatherReportResponse> responseEntity = restTemplate.getForEntity(
            weatherApi.buildCurrentWeatherUrl(countryCode, city),
            WeatherReportResponse.class
        );

        return mapWeatherReportResponse(responseEntity.getBody());
    }

    public WeatherReport[] getWeatherForecastForCity(Integer dayNumber, String countryCode, String city) {
        ResponseEntity<WeatherForecastResponse> responseEntity = restTemplate.getForEntity(
            weatherApi.buildWeatherForecastUrl(countryCode, city),
            WeatherForecastResponse.class
        );
        WeatherReportResponse[] weatherReportResponses = responseEntity.getBody().getList();
        WeatherReport[] weatherReportArray = new WeatherReport[weatherReportResponses.length];
        int weatherReportArrayIndex = 0;
        for (int i = 0; i < weatherReportResponses.length; i++) {
            WeatherReport weatherReport = mapWeatherReportResponse(weatherReportResponses[i]);
            if (weatherReportBelongsToDayNumber(weatherReport, dayNumber)) {
                weatherReportArray[weatherReportArrayIndex++] = weatherReport;
            }
        }

        return weatherReportArray;
    }

    private WeatherReport mapWeatherReportResponse(WeatherReportResponse weatherReportResponse) {
        LinkedHashMap<String, String> weatherData = null;
        try {
            weatherData = weatherReportResponse.getWeather()[0];
        } catch (NullPointerException ignore) {
        }
        LinkedHashMap<String, Double> rainData = weatherReportResponse.getRain();

        return new WeatherReport(
            weatherData != null ? weatherData.get("main") : null,
            weatherData != null ? weatherData.get("description") : null,
            weatherData != null ? weatherData.get("icon") : null,
            weatherReportResponse.getMain().get("temp"),
            weatherReportResponse.getMain().get("temp_min"),
            weatherReportResponse.getMain().get("temp_max"),
            weatherReportResponse.getMain().get("pressure"),
            weatherReportResponse.getMain().get("humidity"),
            weatherReportResponse.getVisibility(),
            weatherReportResponse.getWind().get("speed"),
            rainData != null ? rainData.getOrDefault("1h", null) : null,
            rainData != null ? rainData.getOrDefault("3h", null) : null,
            weatherReportResponse.getClouds().get("all"),
            weatherReportResponse.getDt()
        );
    }

    private Boolean weatherReportBelongsToDayNumber(WeatherReport weatherReport, Integer dayNumber) {
        return true;
    }
}

package com.your_city_weather.service;

import com.your_city_weather.api.WeatherApi;
import com.your_city_weather.api.WeatherForecastResponse;
import com.your_city_weather.api.WeatherResponse;
import com.your_city_weather.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.stream.Stream;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherApi weatherApi;

    public Weather getCurrentWeatherForCity(String countryCode, String city) {
        ResponseEntity<WeatherResponse> responseEntity = restTemplate.getForEntity(
            weatherApi.buildCurrentWeatherUrl(countryCode, city),
            WeatherResponse.class
        );

        return mapWeatherResponse(responseEntity.getBody());
    }

    public Weather[] getWeatherForecastForCity(Integer numberOfDay, String countryCode, String city) {
        ResponseEntity<WeatherForecastResponse> responseEntity = restTemplate.getForEntity(
            weatherApi.buildFiveDayWeatherForecastUrl(countryCode, city),
            WeatherForecastResponse.class
        );
        WeatherResponse[] weatherResponses = responseEntity.getBody().getList();
        Weather[] weatherArray = new Weather[weatherResponses.length];
        for (int i = 0; i < weatherResponses.length; i++) {
            weatherArray[i] = mapWeatherResponse(weatherResponses[i]);
        }

        return weatherArray;
    }

    private Weather mapWeatherResponse(WeatherResponse weatherResponse) {
        LinkedHashMap<String, String> weatherData = null;
        try {
            weatherData = weatherResponse.getWeather()[0];
        } catch (NullPointerException ignore) {
        }
        LinkedHashMap<String, Double> rainData = weatherResponse.getRain();

        return new Weather(
            weatherData != null ? weatherData.get("main") : null,
            weatherData != null ? weatherData.get("description") : null,
            weatherData != null ? weatherData.get("icon") : null,
            weatherResponse.getMain().get("temp"),
            weatherResponse.getMain().get("temp_min"),
            weatherResponse.getMain().get("temp_max"),
            weatherResponse.getMain().get("pressure"),
            weatherResponse.getMain().get("humidity"),
            weatherResponse.getVisibility(),
            weatherResponse.getWind().get("speed"),
            rainData != null ? rainData.getOrDefault("1h", null) : null,
            rainData != null ? rainData.getOrDefault("3h", null) : null,
            weatherResponse.getClouds().get("all"),
            weatherResponse.getDt()
        );
    }
}

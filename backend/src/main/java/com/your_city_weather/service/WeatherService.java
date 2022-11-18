package com.your_city_weather.service;

import com.your_city_weather.api.WeatherApi;
import com.your_city_weather.api.CurrentWeatherResponse;
import com.your_city_weather.api.FiveDayWeatherForecastResponse;
import com.your_city_weather.model.CurrentWeather;
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

    public CurrentWeather getCurrentWeatherByCityName(String city) {
        ResponseEntity<CurrentWeatherResponse> responseEntity = restTemplate.getForEntity(
            weatherApi.buildCurrentWeatherUrl(city),
            CurrentWeatherResponse.class
        );
        CurrentWeatherResponse currentWeatherResponse = responseEntity.getBody();
        LinkedHashMap<String, String> weatherData = null;
        try {
            weatherData = currentWeatherResponse.getWeather()[0];
        } catch (NullPointerException ignore) {
        }
        LinkedHashMap<String, Double> rainData = currentWeatherResponse.getRain();

        return new CurrentWeather(
            weatherData != null ? weatherData.get("main") : null,
            weatherData != null ? weatherData.get("description") : null,
            weatherData != null ? weatherData.get("icon") : null,
            currentWeatherResponse.getMain().get("temp"),
            currentWeatherResponse.getMain().get("temp_min"),
            currentWeatherResponse.getMain().get("temp_max"),
            currentWeatherResponse.getMain().get("pressure"),
            currentWeatherResponse.getMain().get("humidity"),
            currentWeatherResponse.getVisibility(),
            currentWeatherResponse.getWind().get("speed"),
            rainData != null ? rainData.getOrDefault("1h", null) : null,
            rainData != null ? rainData.getOrDefault("3h", null) : null,
            currentWeatherResponse.getClouds().get("all")
        );
    }

    public FiveDayWeatherForecastResponse getFiveDayWeatherForecastByCityName(String city) {
        ResponseEntity<FiveDayWeatherForecastResponse> responseEntity = restTemplate.getForEntity(
            weatherApi.buildFiveDayWeatherForecastUrl(city),
            FiveDayWeatherForecastResponse.class
        );
        FiveDayWeatherForecastResponse fiveDayWeatherForecastResponse = responseEntity.getBody();

        return fiveDayWeatherForecastResponse;
    }
}

package com.your_city_weather.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeatherApi {

    private final String currentWeatherBaseUrl = "https://api.openweathermap.org/data/2.5/weather";

    @Value("${your_city_weather.openweather-apikey}")
    private String apiKey;

    public String buildCurrentWeatherUrl(Double latitude, Double longitude) {
        return currentWeatherBaseUrl + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey;
    }
}

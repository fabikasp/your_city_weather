package com.your_city_weather.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeatherApi {

    private final String currentWeatherBaseUrl = "https://api.openweathermap.org/data/2.5/weather";
    private final String weatherForecastFor5DaysBaseUrl = "https://api.openweathermap.org/data/2.5/forecast";

    @Value("${your_city_weather.openweather-apikey}")
    private String apiKey;

    public String buildCurrentWeatherUrl(String city) {
        return currentWeatherBaseUrl + "?q=" + city + "&appid=" + apiKey;
    }

    public String buildWeatherForecastFor5DaysUrl(String city) {
        return weatherForecastFor5DaysBaseUrl + "?q=" + city + "&appid=" + apiKey;
    }
}

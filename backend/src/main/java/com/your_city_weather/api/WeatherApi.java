package com.your_city_weather.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeatherApi {

    private final String currentWeatherBaseUrl = "https://api.openweathermap.org/data/2.5/weather";
    private final String weatherForecastBaseUrl = "https://api.openweathermap.org/data/2.5/forecast";

    @Value("${your_city_weather.openweather-apikey}")
    private String apiKey;

    public String buildCurrentWeatherUrl(String countryCode, String city) {
        return currentWeatherBaseUrl + "?q=" + city + "," + countryCode + "&appid=" + apiKey;
    }

    public String buildWeatherForecastUrl(String countryCode, String city) {
        return weatherForecastBaseUrl + "?q=" + city + "," + countryCode + "&appid=" + apiKey;
    }
}

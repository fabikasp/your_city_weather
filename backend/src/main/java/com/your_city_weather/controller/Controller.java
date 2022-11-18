package com.your_city_weather.controller;

import com.your_city_weather.model.CurrentWeather;
import com.your_city_weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/current-weather/{city}")
    public CurrentWeather currentWeather(@PathVariable String city) {
        return weatherService.getCurrentWeatherByCityName(city);
    }
}

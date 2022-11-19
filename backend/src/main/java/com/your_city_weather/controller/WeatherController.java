package com.your_city_weather.controller;

import com.your_city_weather.model.WeatherReport;
import com.your_city_weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/current-weather/{countryCode}/{city}")
    public WeatherReport currentWeather(@PathVariable("countryCode") String countryCode, @PathVariable("city") String city) {
        return weatherService.getCurrentWeatherForCity(countryCode, city);
    }

    @GetMapping("/weather-forecast/{numberOfDay}/{countryCode}/{city}")
    public WeatherReport[] weatherForecast(
        @PathVariable("numberOfDay") Integer numberOfDay, /* 0 = today, 1 = tomorrow, ..., 5 = in 5 days */
        @PathVariable("countryCode") String countryCode,
        @PathVariable("city") String city
    ) {
        return weatherService.getWeatherForecastForCity(numberOfDay, countryCode, city);
    }
}

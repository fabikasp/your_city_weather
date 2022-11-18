package com.your_city_weather.controller;

import com.your_city_weather.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final WeatherService weatherService;

    public Controller(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/test")
    public String test() {
        return weatherService.test();
    }
}

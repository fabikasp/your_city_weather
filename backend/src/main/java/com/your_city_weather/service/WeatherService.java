package com.your_city_weather.service;

import com.your_city_weather.model.WeatherApi;
import com.your_city_weather.model.CurrentWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherApi weatherApi;

    public CurrentWeather getCurrentWeatherByCityName(String city) {
        ResponseEntity<CurrentWeather> responseEntity = restTemplate.getForEntity(
            weatherApi.buildCurrentWeatherUrl(city),
            CurrentWeather.class
        );

        return responseEntity.getBody();
    }
}

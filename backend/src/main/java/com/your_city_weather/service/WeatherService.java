package com.your_city_weather.service;

import com.your_city_weather.api.WeatherApi;
import com.your_city_weather.api.WeatherForecastResponse;
import com.your_city_weather.api.WeatherReportResponse;
import com.your_city_weather.model.WeatherReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherApi weatherApi;

    public WeatherReport getCurrentWeatherForCity(String countryCode, String city) {
        try {
            ResponseEntity<WeatherReportResponse> responseEntity = restTemplate.getForEntity(
                weatherApi.buildCurrentWeatherUrl(countryCode, city),
                WeatherReportResponse.class
            );

            return mapWeatherReportResponse(responseEntity.getBody());
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        }
    }

    public WeatherReport[] getWeatherForecastForCity(Integer dayNumber, String countryCode, String city) {
        try {
            ResponseEntity<WeatherForecastResponse> responseEntity = restTemplate.getForEntity(
                weatherApi.buildWeatherForecastUrl(countryCode, city),
                WeatherForecastResponse.class
            );

            WeatherReportResponse[] weatherReportResponses = responseEntity.getBody().getList();
            ArrayList<WeatherReport> weatherReports = new ArrayList<>();
            for (WeatherReportResponse weatherReportResponse : weatherReportResponses) {
                WeatherReport weatherReport = mapWeatherReportResponse(weatherReportResponse);
                if (weatherReportBelongsToDayNumber(weatherReport, dayNumber)) {
                    weatherReports.add(weatherReport);
                }
            }

            return weatherReports.toArray(new WeatherReport[0]);
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        }
    }

    private WeatherReport mapWeatherReportResponse(WeatherReportResponse weatherReportResponse) {
        LinkedHashMap<String, String> weatherData = null;
        try {
            weatherData = weatherReportResponse.getWeather()[0];
        } catch (NullPointerException ignore) {
        }
        LinkedHashMap<String, Double> windData = weatherReportResponse.getWind();
        LinkedHashMap<String, Double> rainData = weatherReportResponse.getRain();

        return new WeatherReport(
            weatherData != null ? weatherData.getOrDefault("main", null) : null,
            weatherData != null ? weatherData.getOrDefault("description", null) : null,
            weatherData != null ? weatherData.getOrDefault("icon", null) : null,
            weatherReportResponse.getMain().getOrDefault("temp", null),
            weatherReportResponse.getMain().getOrDefault("temp_min", null),
            weatherReportResponse.getMain().getOrDefault("temp_max", null),
            weatherReportResponse.getMain().getOrDefault("pressure", null),
            weatherReportResponse.getMain().getOrDefault("humidity", null),
            weatherReportResponse.getVisibility(),
            windData != null ? windData.getOrDefault("speed", null) : null,
            rainData != null ? rainData.getOrDefault("1h", null) : null,
            rainData != null ? rainData.getOrDefault("3h", null) : null,
            weatherReportResponse.getClouds().getOrDefault("all", null),
            new Date((long) weatherReportResponse.getDt() * 1000)
        );
    }

    private Boolean weatherReportBelongsToDayNumber(WeatherReport weatherReport, Integer dayNumber) {
        Calendar consideredDate = Calendar.getInstance();
        consideredDate.setTime(new Date());
        consideredDate.add(Calendar.DATE, dayNumber);

        Calendar weatherReportDate = Calendar.getInstance();
        weatherReportDate.setTime(weatherReport.timestamp());

        return consideredDate.get(Calendar.DAY_OF_MONTH) == weatherReportDate.get(Calendar.DAY_OF_MONTH);
    }
}

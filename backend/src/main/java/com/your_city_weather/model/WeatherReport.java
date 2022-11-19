package com.your_city_weather.model;

public record WeatherReport(
    String weatherName,
    String weatherDescription,
    String weatherIconCode,
    Double temperature,
    Double minTemperature,
    Double maxTemperature,
    Double pressure,
    Double humidity,
    Integer visibilityRange,
    Double windSpeed,
    Double rainVolume1Hour,
    Double rainVolume3Hours,
    Double cloudiness,
    Integer timestamp
) {
}

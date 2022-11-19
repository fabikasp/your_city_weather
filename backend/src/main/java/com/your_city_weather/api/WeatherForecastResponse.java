package com.your_city_weather.api;

public class WeatherForecastResponse {

    private WeatherResponse[] list;

    public WeatherForecastResponse(WeatherResponse[] list) {
        this.list = list;
    }

    public WeatherForecastResponse() {
    }

    public WeatherResponse[] getList() {
        return list;
    }
}

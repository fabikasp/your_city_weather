package com.your_city_weather.api;

public class WeatherForecastResponse {

    private WeatherReportResponse[] list;

    public WeatherForecastResponse(WeatherReportResponse[] list) {
        this.list = list;
    }

    public WeatherForecastResponse() {
    }

    public WeatherReportResponse[] getList() {
        return list;
    }
}

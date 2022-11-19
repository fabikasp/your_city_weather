package com.your_city_weather.api;

import java.util.LinkedHashMap;

public class WeatherResponse {

    private LinkedHashMap<String, String>[] weather;

    private LinkedHashMap<String, Double> main;

    private Integer visibility;

    private LinkedHashMap<String, Double> wind;

    private LinkedHashMap<String, Double> rain;

    private LinkedHashMap<String, Double> clouds;

    private Integer dt;

    public WeatherResponse(
        LinkedHashMap<String, String>[] weather,
        LinkedHashMap<String, Double> main,
        Integer visibility, LinkedHashMap<String, Double> wind,
        LinkedHashMap<String, Double> rain,
        LinkedHashMap<String, Double> clouds,
        Integer dt
    ) {
        this.weather = weather;
        this.main = main;
        this.visibility = visibility;
        this.wind = wind;
        this.rain = rain;
        this.clouds = clouds;
        this.dt = dt;
    }

    public WeatherResponse() {
    }

    public LinkedHashMap<String, String>[] getWeather() {
        return weather;
    }

    public LinkedHashMap<String, Double> getMain() {
        return main;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public LinkedHashMap<String, Double> getWind() {
        return wind;
    }

    public LinkedHashMap<String, Double> getRain() {
        return rain;
    }

    public LinkedHashMap<String, Double> getClouds() {
        return clouds;
    }

    public Integer getDt() {
        return dt;
    }
}

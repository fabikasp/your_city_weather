package com.your_city_weather.api;

import java.util.LinkedHashMap;

public class CurrentWeatherResponse {

    private LinkedHashMap<String, Double> coord;

    private LinkedHashMap<String, String>[] weather;

    private String base;

    private LinkedHashMap<String, Double> main;

    private Integer visibility;

    private LinkedHashMap<String, Double> wind;

    private LinkedHashMap<String, Double> rain;

    private LinkedHashMap<String, Double> clouds;

    private Integer dt;

    private LinkedHashMap<String, Object> sys;

    private Integer timezone;

    private Integer id;

    private String name;

    private Integer cod;


    public CurrentWeatherResponse(
        LinkedHashMap<String, Double> coord,
        LinkedHashMap<String, String>[] weather,
        String base,
        LinkedHashMap<String, Double> main,
        Integer visibility, LinkedHashMap<String, Double> wind,
        LinkedHashMap<String, Double> rain,
        LinkedHashMap<String, Double> clouds,
        Integer dt,
        LinkedHashMap<String, Object> sys,
        Integer timezone,
        Integer id,
        String name,
        Integer cod
    ) {
        this.coord = coord;
        this.weather = weather;
        this.base = base;
        this.main = main;
        this.visibility = visibility;
        this.wind = wind;
        this.rain = rain;
        this.clouds = clouds;
        this.dt = dt;
        this.sys = sys;
        this.timezone = timezone;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }

    public CurrentWeatherResponse() {
    }

    public LinkedHashMap<String, Double> getCoord() {
        return coord;
    }

    public LinkedHashMap<String, String>[] getWeather() {
        return weather;
    }

    public String getBase() {
        return base;
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

    public LinkedHashMap<String, Object> getSys() {
        return sys;
    }

    public Integer getTimezone() {
        return timezone;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCod() {
        return cod;
    }
}

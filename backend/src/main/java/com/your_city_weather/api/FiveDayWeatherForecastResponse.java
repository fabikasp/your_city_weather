package com.your_city_weather.api;

public class FiveDayWeatherForecastResponse {

    private String cod;

    private Double message;

    private Integer cnt;

    private Object[] list;

    public FiveDayWeatherForecastResponse(String cod, Double message, Integer cnt, Object[] list) {
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.list = list;
    }

    public FiveDayWeatherForecastResponse() {
    }

    public String getCod() {
        return cod;
    }

    public Double getMessage() {
        return message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public Object[] getList() {
        return list;
    }
}

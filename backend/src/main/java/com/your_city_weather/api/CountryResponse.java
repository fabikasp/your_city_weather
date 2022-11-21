package com.your_city_weather.api;

import java.util.LinkedHashMap;

public class CountryResponse {

    private LinkedHashMap<String, Object> name;

    private String cca3;

    public CountryResponse(LinkedHashMap<String, Object> name, String cca3) {
        this.name = name;
        this.cca3 = cca3;
    }

    public CountryResponse() {
    }

    public LinkedHashMap<String, Object> getName() {
        return name;
    }

    public String getCca3() {
        return cca3;
    }
}

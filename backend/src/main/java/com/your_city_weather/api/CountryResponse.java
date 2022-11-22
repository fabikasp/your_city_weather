package com.your_city_weather.api;

import java.util.LinkedHashMap;

public class CountryResponse {

    private LinkedHashMap<String, LinkedHashMap<String, String>> translations;

    private String cca2;

    public CountryResponse(LinkedHashMap<String, LinkedHashMap<String, String>> translations, String cca2) {
        this.translations = translations;
        this.cca2 = cca2;
    }

    public CountryResponse() {
    }

    public LinkedHashMap<String, LinkedHashMap<String, String>> getTranslations() {
        return translations;
    }

    public String getCca2() {
        return cca2;
    }
}

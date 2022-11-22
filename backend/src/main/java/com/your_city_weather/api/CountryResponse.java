package com.your_city_weather.api;

import java.util.LinkedHashMap;

public class CountryResponse {

    private LinkedHashMap<String, LinkedHashMap<String, String>> translations;

    private String cca3;

    public CountryResponse(LinkedHashMap<String, LinkedHashMap<String, String>> translations, String cca3) {
        this.translations = translations;
        this.cca3 = cca3;
    }

    public CountryResponse() {
    }

    public LinkedHashMap<String, LinkedHashMap<String, String>> getTranslations() {
        return translations;
    }

    public String getCca3() {
        return cca3;
    }
}

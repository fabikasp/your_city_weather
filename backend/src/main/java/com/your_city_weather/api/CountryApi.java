package com.your_city_weather.api;

import org.springframework.stereotype.Component;

@Component
public class CountryApi {

    public static final String allCountriesUrl = "https://restcountries.com/v3.1/all";

    private final String countriesByNameBaseUrl = "https://restcountries.com/v3.1/name/";

    public String buildCountriesByNameUrl(String countryName) {
        return countriesByNameBaseUrl + countryName;
    }
}

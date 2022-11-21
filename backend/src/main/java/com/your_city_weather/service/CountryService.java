package com.your_city_weather.service;

import com.your_city_weather.api.*;
import com.your_city_weather.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CountryService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CountryApi countryApi;

    public Country[] getCountries() {
        return handleCountryRequest(CountryApi.allCountriesUrl);
    }

    public Country[] getCountriesByName(String countryName) {
        return handleCountryRequest(countryApi.buildCountriesByNameUrl(countryName));
    }

    private Country[] handleCountryRequest(String requestUrl) {
        try {
            ResponseEntity<CountryResponse[]> responseEntity = restTemplate.getForEntity(
                requestUrl,
                CountryResponse[].class
            );

            CountryResponse[] countryResponses = responseEntity.getBody();
            Country[] countries = new Country[countryResponses.length];
            for (int i = 0; i < countryResponses.length; i++) {
                countries[i] = mapCountryResponse(countryResponses[i]);
            }

            return countries;
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        }
    }

    private Country mapCountryResponse(CountryResponse countryResponse) {
        return new Country(
            (String) countryResponse.getName().getOrDefault("common", null),
            countryResponse.getCca3()
        );
    }
}

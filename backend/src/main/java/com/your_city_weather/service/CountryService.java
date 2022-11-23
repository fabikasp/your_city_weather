package com.your_city_weather.service;

import com.your_city_weather.api.CountryApi;
import com.your_city_weather.api.CountryResponse;
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

    public String[] getCountryNames() {
        try {
            ResponseEntity<CountryResponse[]> responseEntity = restTemplate.getForEntity(
                CountryApi.allCountriesUrl,
                CountryResponse[].class
            );

            CountryResponse[] countryResponses = responseEntity.getBody();
            String[] countryNames = new String[countryResponses.length];
            for (int i = 0; i < countryResponses.length; i++) {
                countryNames[i] = countryResponses[i].getTranslations().get("deu").get("common");
            }

            return countryNames;
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        }
    }

    public String getCountryCodeByName(String countryName) {
        try {
            ResponseEntity<CountryResponse[]> responseEntity = restTemplate.getForEntity(
                countryApi.buildCountriesByNameUrl(countryName),
                CountryResponse[].class
            );
            CountryResponse[] countryResponses = responseEntity.getBody();

            return countryResponses[0].getCca2();
        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(e.getStatusCode(), e.getMessage(), e);
        }
    }
}

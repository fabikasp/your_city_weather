package com.your_city_weather.service;

import com.your_city_weather.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CountryServiceTest {

    @Autowired
    private CountryService countryService;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private CountryApi countryApi;

    private CountryResponse[] countryResponses;

    private String[] countryNames;

    @BeforeEach
    void setUp() {
        LinkedHashMap<String, LinkedHashMap<String, String>> translationsMap = new LinkedHashMap<>();
        LinkedHashMap<String, String> deuMap = new LinkedHashMap<>();
        deuMap.put("common", "Deutschland");
        translationsMap.put("deu", deuMap);
        countryResponses = new CountryResponse[] { new CountryResponse(translationsMap, "DEU") };
        countryNames = new String[] { "Deutschland" };
        Mockito.when(countryApi.buildCountriesByNameUrl("Deutschland"))
            .thenReturn("testCountriesByNameUrl");
    }

    @Test
    void testGetCountryNamesError() {
        Mockito.when(restTemplate.getForEntity(CountryApi.allCountriesUrl, CountryResponse[].class))
            .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseStatusException responseStatusException = assertThrows(
            ResponseStatusException.class,
            () -> countryService.getCountryNames()
        );
        assertSame(responseStatusException.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testGetCountryNamesSuccess() {
        ResponseEntity<CountryResponse[]> responseEntity = new ResponseEntity<>(
            countryResponses,
            new HttpHeaders(),
            HttpStatus.OK
        );
        Mockito.when(restTemplate.getForEntity(CountryApi.allCountriesUrl, CountryResponse[].class))
            .thenReturn(responseEntity);
        assertThat(countryService.getCountryNames()).isEqualTo(countryNames);
    }

    @Test
    void testGetCountryCodeByNameError() {
        Mockito.when(restTemplate.getForEntity("testCountriesByNameUrl", CountryResponse[].class))
            .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        ResponseStatusException responseStatusException = assertThrows(
            ResponseStatusException.class,
            () -> countryService.getCountryCodeByName("Deutschland")
        );
        assertSame(responseStatusException.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    void testGetCountryCodeByNameSuccess() {
        ResponseEntity<CountryResponse[]> responseEntity = new ResponseEntity<>(
            countryResponses,
            new HttpHeaders(),
            HttpStatus.OK
        );
        Mockito.when(restTemplate.getForEntity("testCountriesByNameUrl", CountryResponse[].class))
            .thenReturn(responseEntity);
        assertThat(countryService.getCountryCodeByName("Deutschland")).isEqualTo("DEU");
    }
}

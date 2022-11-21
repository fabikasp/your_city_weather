package com.your_city_weather.service;

import com.your_city_weather.api.*;
import com.your_city_weather.model.Country;
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

    private Country[] countries;

    @BeforeEach
    void setUp() {
        LinkedHashMap<String, Object> nameMap = new LinkedHashMap<>();
        nameMap.put("common", "Germany");
        countryResponses = new CountryResponse[] { new CountryResponse(nameMap, "DEU") };
        countries = new Country[] { new Country("Germany", "DEU") };
        Mockito.when(countryApi.buildCountriesByNameUrl("Germany"))
            .thenReturn("testCountriesByNameUrl");
    }

    @Test
    void testGetCountriesError() {
        Mockito.when(restTemplate.getForEntity(CountryApi.allCountriesUrl, CountryResponse[].class))
            .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));
        ResponseStatusException responseStatusException = assertThrows(
            ResponseStatusException.class,
            () -> countryService.getCountries()
        );
        assertSame(responseStatusException.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testGetCountriesSuccess() {
        ResponseEntity<CountryResponse[]> responseEntity = new ResponseEntity<>(
            countryResponses,
            new HttpHeaders(),
            HttpStatus.OK
        );
        Mockito.when(restTemplate.getForEntity(CountryApi.allCountriesUrl, CountryResponse[].class))
            .thenReturn(responseEntity);
        assertThat(countryService.getCountries()).isEqualTo(countries);
    }

    @Test
    void testGetCountriesByNameError() {
        Mockito.when(restTemplate.getForEntity("testCountriesByNameUrl", CountryResponse[].class))
            .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        ResponseStatusException responseStatusException = assertThrows(
            ResponseStatusException.class,
            () -> countryService.getCountriesByName("Germany")
        );
        assertSame(responseStatusException.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    void testGetCountriesByNameSuccess() {
        ResponseEntity<CountryResponse[]> responseEntity = new ResponseEntity<>(
            countryResponses,
            new HttpHeaders(),
            HttpStatus.OK
        );
        Mockito.when(restTemplate.getForEntity("testCountriesByNameUrl", CountryResponse[].class))
            .thenReturn(responseEntity);
        assertThat(countryService.getCountriesByName("Germany")).isEqualTo(countries);
    }
}

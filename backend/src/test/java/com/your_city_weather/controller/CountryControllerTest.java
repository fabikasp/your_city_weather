package com.your_city_weather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.your_city_weather.model.Country;
import com.your_city_weather.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CountryControllerTest {

    @MockBean
    private CountryService countryService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Country[] countries;

    @BeforeEach
    void setUp() {
        countries = new Country[] { new Country("Germany", "DEU") };
    }

    @Test
    void testCountriesInternalServerError() throws Exception {
        Mockito.when(countryService.getCountries())
            .thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        mvc.perform(get("/countries")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void testCountriesSuccess() throws Exception {
        Mockito.when(countryService.getCountries()).thenReturn(countries);
        final String expectedResponse = objectMapper.writeValueAsString(countries);
        mvc.perform(get("/countries")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(expectedResponse));
    }

    @Test
    void testCountriesByNameNotFound() throws Exception {
        Mockito.when(countryService.getCountriesByName("NichtExistierendesLand"))
            .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        mvc.perform(get("/countries/NichtExistierendesLand")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testCountriesByNameInternalServerError() throws Exception {
        Mockito.when(countryService.getCountriesByName("Germany"))
            .thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        mvc.perform(get("/countries/Germany")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void testCountriesByNameSuccess() throws Exception {
        Mockito.when(countryService.getCountriesByName("Germany")).thenReturn(countries);
        final String expectedResponse = objectMapper.writeValueAsString(countries);
        mvc.perform(get("/countries/Germany")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(expectedResponse));
    }
}

package com.your_city_weather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.your_city_weather.service.CountryService;
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

    @Test
    void testCountryNamesInternalServerError() throws Exception {
        Mockito.when(countryService.getCountryNames())
            .thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        mvc.perform(get("/countryNames")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void testCountryNamesSuccess() throws Exception {
        String[] countryNames = new String[] { "Germany" };
        Mockito.when(countryService.getCountryNames()).thenReturn(countryNames);
        mvc.perform(get("/countryNames")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(countryNames)));
    }

    @Test
    void testCountryCodeByNameNotFound() throws Exception {
        Mockito.when(countryService.getCountryCodeByName("NichtExistierendesLand"))
            .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        mvc.perform(get("/countryCode/NichtExistierendesLand")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void testCountryCodeByNameInternalServerError() throws Exception {
        Mockito.when(countryService.getCountryCodeByName("Germany"))
            .thenThrow(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        mvc.perform(get("/countryCode/Germany")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());
    }

    @Test
    void testCountryCodeByNameSuccess() throws Exception {
        Mockito.when(countryService.getCountryCodeByName("Germany")).thenReturn("DEU");
        mvc.perform(get("/countryCode/Germany")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("DEU"));
    }
}

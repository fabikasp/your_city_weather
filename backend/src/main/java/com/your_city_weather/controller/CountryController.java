package com.your_city_weather.controller;

import com.your_city_weather.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping( "/countryNames")
    public String[] countryNames() {
        return countryService.getCountryNames();
    }

    @GetMapping( "/countryCode/{countryName}")
    public String countryCodeByName(@PathVariable String countryName) {
        return countryService.getCountryCodeByName(countryName);
    }
}

package com.your_city_weather.controller;

import com.your_city_weather.model.Country;
import com.your_city_weather.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping({ "/countries", "/countries/{countryName}" })
    public Country[] countries(@PathVariable(required = false) String countryName) {
        if (countryName == null) {
            return countryService.getCountries();
        }

        return countryService.getCountriesByName(countryName);
    }
}

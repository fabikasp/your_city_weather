import React, { useState } from "react";
import { CitySelection } from "./city-selection/CitySelection";
import { CurrentWeatherWidget } from "./current-weather/CurrentWeatherWidget";
import "./App.css";
import { WeatherForecastContainer } from "./weather-forecast/WeatherForecastContainer";

export const App = () => {
    const [searchedCountryCode, setSearchedCountryCode] = useState<string>("");
    const [searchedCity, setSearchedCity] = useState<string>("");

    return (
        <div id="app-container">
            <CitySelection
                searchedCity={searchedCity}
                setSearchedCountryCode={setSearchedCountryCode}
                setSearchedCity={setSearchedCity}
            />
            {searchedCity != "" && <CurrentWeatherWidget searchedCountryCode={searchedCountryCode} searchedCity={searchedCity} />}
            {searchedCity != "" && <WeatherForecastContainer searchedCountryCode={searchedCountryCode} searchedCity={searchedCity} />}
        </div>
    );
};

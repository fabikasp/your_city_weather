import React, { useState } from "react";
import { CitySelection } from "./city-selection/CitySelection";
import { CurrentWeatherWidget } from "./current-weather/CurrentWeatherWidget";
import "./App.css";

export const App = () => {
    const [searchedCountryCode, setSearchedCountryCode] = useState<string>("DE");
    const [searchedCity, setSearchedCity] = useState<string>("");

    return (
        <div id="app-container">
            <CitySelection 
                searchedCountryCode={searchedCountryCode} 
                searchedCity={searchedCity}
                setSearchedCountryCode={setSearchedCountryCode}
                setSearchedCity={setSearchedCity}
            />
            {searchedCity != "" && <CurrentWeatherWidget searchedCountryCode={searchedCountryCode} searchedCity={searchedCity} />}
        </div>
    );

    // TODO: Auflistung der Forecast Widgets (in Forecast Widget Container, der Selection der Tage zur Verfügung stellt)
};

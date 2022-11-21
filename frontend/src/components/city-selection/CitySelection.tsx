import React, { useState, useEffect } from "react";
import Axios from "axios";
import { CitySelectionProps } from "./types";
import "./CitySelection.css";

export const CitySelection = ({ searchedCountryCode, searchedCity, setSearchedCountryCode, setSearchedCity }: CitySelectionProps) => {
    // TODO: Backend-API für Länder bauen

    return (
        <div id="city-selection-container">
            <select id="city-selection-country-dropdown">
                <option>Deutschland</option>
            </select>
            <input 
                id="city-selection-city-input" 
                type="text" 
                placeholder="Stadt" 
                value={searchedCity} 
                onChange={event => setSearchedCity(event.target.value)} 
            />
        </div>
    );
};

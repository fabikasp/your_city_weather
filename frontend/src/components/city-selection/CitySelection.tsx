import React, { useState, useEffect } from "react";
import Axios from "axios";
import { CitySelectionProps, COUNTRY_CODE_BY_NAME_URL, COUNTRY_NAMES_URL } from "./types";
import "./CitySelection.css";

export const CitySelection = ({ searchedCity, setSearchedCountryCode, setSearchedCity }: CitySelectionProps) => {
    const [countryNameOptions, setCountryNameOptions] = useState<JSX.Element[]>([]);
    const [currentCountryName, setCurrentCountryName] = useState("Deutschland");

    async function getCountryNames(): Promise<string[]> {
        try {
            const { data, status } = await Axios.get<string[]>(
                COUNTRY_NAMES_URL,
                { headers: { Accept: 'application/json' } }
            );

            if (status != 200) {
                return ["Deutschland"];
            }
            
            return data;
        } catch (e) {
            return ["Deutschland"];
        }
    }

    async function getCountryCodeByName(countryName: string): Promise<string> {
        try {
            const { data, status } = await Axios.get<string>(
                COUNTRY_CODE_BY_NAME_URL.replace("{countryName}", countryName),
                { headers: { Accept: 'application/json' } }
            );

            if (status != 200) {
                setCurrentCountryName("Deutschland");

                return "DE";
            }
            
            return data;
        } catch (e) {
            setCurrentCountryName("Deutschland");

            return "DE";
        }
    }

    useEffect(() => {
        getCountryNames().then(countryNames => {
            let mappedCountryNameOptions: JSX.Element[] = [];
            countryNames.forEach((countryName) => {
                mappedCountryNameOptions.push(<option key={countryName} value={countryName}>{countryName}</option>);
            });
            setCountryNameOptions(mappedCountryNameOptions);
        });
    }, []);

    useEffect(() => {
        getCountryCodeByName(currentCountryName).then(countryCode => setSearchedCountryCode(countryCode));
    }, [currentCountryName]);

    return (
        <div id="city-selection-container">
            <select id="city-selection-country-dropdown" onChange={event => setCurrentCountryName(event.target.value)} value={currentCountryName}>
                {countryNameOptions}
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

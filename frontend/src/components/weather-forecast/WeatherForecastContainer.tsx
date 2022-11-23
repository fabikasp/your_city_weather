import React, { useState, useEffect } from "react";
import Axios from "axios";
import BeatLoader from "react-spinners/BeatLoader";
import { DAYS_OF_WEEK, WeatherForecastContainerProps, WEATHER_FORECAST_URL } from "./types";
import { Weather } from "../shared/types";
import { WeatherForecastWidget } from "./WeatherForecastWidget";
import "./WeatherForecastContainer.css";

export const WeatherForecastContainer = ({ searchedCountryCode, searchedCity }: WeatherForecastContainerProps) => {
    const [currentWeatherForecast, setCurrentWeatherForecast] = useState<Weather[]|undefined>(undefined);
    const [listedDays, setListedDays] = useState<string[]>([]);
    const [currentDayNumber, setCurrentDayNumber] = useState(0);

    async function getWeatherForecastForDay(dayNumber: number): Promise<Weather[]|undefined> {
        try {
            const { data, status } = await Axios.get<Weather[]>(
                WEATHER_FORECAST_URL.replace("{dayNumber}", dayNumber.toString()).replace("{countryCode}", searchedCountryCode).replace("{city}", searchedCity),
                { headers: { Accept: 'application/json' } }
            );

            if (status != 200) {
                return undefined;
            }
            
            return data;
        } catch (e) {
            return undefined;
        }
    }

    useEffect(() => {
        let days: string[] = ["Heute"];
        const baseDate = new Date();
        for (let i = 1; i <= 5; i++) {
            let consideredDate = new Date(baseDate);
            consideredDate.setDate(consideredDate.getDate() + i);
            days.push(DAYS_OF_WEEK[consideredDate.getDay()]);
        }
        setListedDays(days);
    }, []);

    useEffect(() => {
        setCurrentDayNumber(0);
        getWeatherForecastForDay(0).then(weatherForecast => setCurrentWeatherForecast(weatherForecast));
    }, [searchedCountryCode, searchedCity]);

    useEffect(() => {
        getWeatherForecastForDay(currentDayNumber).then(weatherForecast => setCurrentWeatherForecast(weatherForecast));
    }, [currentDayNumber]);

    const widgetsContainer = (
        <div id="weather-forecast-container">
            <div id="weather-forecast-selection-bar">
                { listedDays.map((listedDay, index) => {
                    const className = "weather-forecast-selection-item" + (index == currentDayNumber ? " selected": "");

                    return <div
                        key={index} 
                        className={className}
                        onClick={() => setCurrentDayNumber(index)}
                    >{listedDay}</div>
                }) }
            </div>
            <div id="weather-forecast-widgets-container">
                { currentWeatherForecast?.map((weatherForecast, index) => <WeatherForecastWidget key={index} weatherForecast={weatherForecast} />) }
            </div>
        </div>
    );

    const spinnerContainer = (
        <div id="weather-forecast-spinner-container">
            <BeatLoader color="#FFFF" loading={true} size={20} />
        </div>
    );

    return currentWeatherForecast ? widgetsContainer : spinnerContainer;
};

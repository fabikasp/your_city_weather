import React, { useState, useEffect } from "react";
import Axios from "axios";
import BeatLoader from "react-spinners/BeatLoader";
import { WeatherForecastContainerProps, WEATHER_FORECAST_URL } from "./types";
import { Weather } from "../shared/types";
import "./WeatherForecast.css";

export const WeatherForecastContainer = ({ searchedCountryCode, searchedCity }: WeatherForecastContainerProps) => {
    const [weatherForecasts, setWeatherForecasts] = useState<{ [dayNumber: number]: Weather[]}>({});

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
        const today = new Date();
        let requestedWeatherForecasts: { [dayNumber: number]: Weather[]} = {};
        for (let i = 0; i < 6; i++) {
            getWeatherForecastForDay(i).then(weatherForecast => {
                if (weatherForecast) {
                    requestedWeatherForecasts[i] = weatherForecast;
                }
            });
        }
        setWeatherForecasts(requestedWeatherForecasts);
    }, [searchedCountryCode, searchedCity]);

    // TODO: Weather[] + Wochentagstring an Widgets übergeben
    // TODO: const consideredDate = new Date(today); consideredDate.setDate(consideredDate.getDate() + i);
    // TODO: Spinner anzeigen, wenn forecasts leer sind
    // TODO: Die Wochentage anzeigen, die von API überliefert wurden (nicht statisch hinschreiben)
    const widgetsContainer = <div>test</div>;

    const spinnerContainer = (
        <div id="weather-forecast-spinner-container">
            <BeatLoader color="#FFFF" loading={true} size={20} />
        </div>
    );

    return spinnerContainer;
};

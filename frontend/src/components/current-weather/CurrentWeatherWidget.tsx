import React, { useState, useEffect } from "react";
import Axios from "axios";
import { CurrentWeather, CurrentWeatherWidgetProps, CURRENT_WEATHER_URL, WEATHER_ICON_URL } from "./types";
import BeatLoader from "react-spinners/BeatLoader";
import "./CurrentWeatherWidget.css";

export const CurrentWeatherWidget = ({ searchedCountryCode, searchedCity }: CurrentWeatherWidgetProps) => {
    const [currentWeather, setCurrentWeather] = useState<CurrentWeather|undefined>(undefined);
    const [weatherIconUrl, setWeatherIconUrl] = useState<string|undefined>(undefined);

    async function getCurrentWeather(): Promise<CurrentWeather|undefined> {
        try {
            const { data, status } = await Axios.get<CurrentWeather>(
                CURRENT_WEATHER_URL.replace("{countryCode}", searchedCountryCode).replace("{city}", searchedCity),
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
        getCurrentWeather().then(currentWeather => {
            setCurrentWeather(currentWeather);

            if (currentWeather) {
                setWeatherIconUrl(WEATHER_ICON_URL.replace("{iconCode}", currentWeather.weatherIconCode));
            }
        });
    }, [searchedCountryCode, searchedCity]);

    const widgetContainer = (
        <div id="current-weather-widget-container">
            <div id="current-weather-conclusion-container">
                <img src={weatherIconUrl} />
                <div>{currentWeather?.weatherDescription}</div>
            </div>
            <div className="current-weather-info-container">
                <div className="current-weather-upper-infos">Temperatur: <span className="current-weather-data">{currentWeather?.temperature} *C</span></div>
                <div className="current-weather-upper-infos">Minimaltemperatur: <span className="current-weather-data">{currentWeather?.minTemperature} *C</span></div>
                <div>Maximaltemperatur: <span className="current-weather-data">{currentWeather?.maxTemperature} *C</span></div>
            </div>
            <div className="current-weather-info-container">
                <div className="current-weather-upper-infos">Bewölkung: <span className="current-weather-data">{currentWeather?.cloudiness ?? 0} %</span></div>
                <div className="current-weather-upper-infos">Windgeschwindigkeit: <span className="current-weather-data">{currentWeather?.windSpeed ?? 0} m/s</span></div>
                <div>Regenvolumen: <span className="current-weather-data">{currentWeather?.rainVolume1Hour ?? 0} mm</span></div>
            </div>
            <div className="current-weather-info-container">
                <div className="current-weather-upper-infos">Blickweite: <span className="current-weather-data">{currentWeather?.visibilityRange} m</span></div>
                <div className="current-weather-upper-infos">Luftfeuchtigkeit: <span className="current-weather-data">{currentWeather?.humidity} %</span></div>
                <div>Luftdruck: <span className="current-weather-data">{currentWeather?.pressure} hPa</span></div>
            </div>
        </div>
    );

    const spinnerContainer = (
        <div id="current-weather-spinner-container">
            <BeatLoader id="current-weather-spinner" color="#FFFF" loading={true} size={20} />
        </div>
    );

    return currentWeather ? widgetContainer : spinnerContainer;
};

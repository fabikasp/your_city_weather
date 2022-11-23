import React from "react";
import { WEATHER_ICON_URL } from "../shared/types";
import { WeatherForecastWidgetProps } from "./types";
import "./WeatherForecastWidget.css";

export const WeatherForecastWidget = ({ weatherForecast }: WeatherForecastWidgetProps) => {
    const timestamp = weatherForecast.timestamp;
    const hours = timestamp.substring(timestamp.indexOf("T") + 1, timestamp.indexOf("T") + 3);
    const minutes = timestamp.substring(timestamp.indexOf("T") + 4, timestamp.indexOf("T") + 6);

    return (
        <div id="weather-forecast-widget">
            <div id="weather-forecast-conclusion-container">
                <img src={WEATHER_ICON_URL.replace("{iconCode}", weatherForecast.weatherIconCode)} />
                <div>{weatherForecast.weatherDescription}</div>
            </div>
            <div id="weather-forecast-info-container">
                <div className="weather-forecast-upper-infos">Zeitpunkt: <span className="weather-forecast-data">{hours}:{minutes} Uhr</span></div>
                <div className="weather-forecast-upper-infos">Temperatur: <span className="weather-forecast-data">{weatherForecast.temperature} *C</span></div>
                <div>Bewölkung: <span className="weather-forecast-data">{weatherForecast.cloudiness} %</span></div>
            </div>
        </div>
    );
};

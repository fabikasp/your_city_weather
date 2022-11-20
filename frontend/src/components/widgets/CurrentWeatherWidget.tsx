import React, { useState, useEffect } from "react";
import Axios from "axios";
import "./CurrentWeatherWidget.css";

type CurrentWeather = {
    weatherName: string,
    weatherDescription: string,
    weatherIconCode: string,
    temperature: number,
    minTemperature: number,
    maxTemperature: number,
    pressure: number,
    humidity: number,
    visibilityRange: number,
    windSpeed: number,
    rainVolume1Hour: number,
    rainVolume3Hours: number,
    cloudiness: number,
    timestamp: string
};

export const CurrentWeatherWidget = () => {
    const [currentWeather, setCurrentWeather] = useState<CurrentWeather|undefined>(undefined);
    const [imageUrl, setImageUrl] = useState<string|undefined>(undefined);

    // TODO: Typen und URLs auslagern
    // TODO: Refactoring, Verbesserung vom ErrorHandling
    // TODO: Spinner anzeigen

    async function getCurrentWeather(): Promise<CurrentWeather|undefined> {
        try {
            const { data, status } = await Axios.get<CurrentWeather>(
                'http://localhost:8080/current-weather/DE/Berlin',
                {
                    headers: { Accept: 'application/json' },
                }
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
        getCurrentWeather().then((currentWeather) => {
            setCurrentWeather(currentWeather);
            setImageUrl("http://openweathermap.org/img/wn/" + currentWeather?.weatherIconCode + "@2x.png");
        });
    }, []);

    // TODO: Nur anzeigen, wenn currentWeather nicht undefined

    return (
        <div id="current-weather-widget-container">
            {currentWeather?.cloudiness}
            <img src={imageUrl} />
        </div>
    );
};

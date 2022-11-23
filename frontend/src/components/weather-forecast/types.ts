import { Weather } from "../shared/types";

export const WEATHER_FORECAST_URL = "http://localhost:8080/weather-forecast/{dayNumber}/{countryCode}/{city}";

export const DAYS_OF_WEEK = ["So", "Mo", "Di", "Mi", "Do", "Fr", "Sa"];

export type WeatherForecastContainerProps = {
    searchedCountryCode: string,
    searchedCity: string
};

export type WeatherForecastWidgetProps = {
    weatherForecast: Weather
};

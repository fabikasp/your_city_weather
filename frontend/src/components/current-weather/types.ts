export const CURRENT_WEATHER_URL = "http://localhost:8080/current-weather/{countryCode}/{city}";

export type CurrentWeatherWidgetProps = {
    searchedCountryCode: string,
    searchedCity: string
};

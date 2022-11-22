export const CURRENT_WEATHER_URL = "http://localhost:8080/current-weather/{countryCode}/{city}";
export const WEATHER_ICON_URL = "http://openweathermap.org/img/wn/{iconCode}@4x.png";

export type CurrentWeatherWidgetProps = {
    searchedCountryCode: string,
    searchedCity: string
};

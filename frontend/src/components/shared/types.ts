export const WEATHER_ICON_URL = "http://openweathermap.org/img/wn/{iconCode}@4x.png";

export type Weather = {
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

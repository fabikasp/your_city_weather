package com.your_city_weather.model;

public class CurrentWeather {

    private final String weatherName;

    private final String weatherDescription;

    private final String weatherIconCode;

    private final Double temperature;

    private final Double minTemperature;

    private final Double maxTemperature;

    private final Double pressure;

    private final Double humidity;

    private final Integer visibilityRange;

    private final Double windSpeed;

    private final Double rainVolume1Hour;

    private final Double rainVolume3Hours;

    private final Double cloudiness;

    public CurrentWeather(
        String weatherName,
        String weatherDescription,
        String weatherIconCode,
        Double temperature,
        Double minTemperature,
        Double maxTemperature,
        Double pressure,
        Double humidity,
        Integer visibilityRange,
        Double windSpeed,
        Double rainVolume1Hour,
        Double rainVolume3Hours,
        Double cloudiness
    ) {
        this.weatherName = weatherName;
        this.weatherDescription = weatherDescription;
        this.weatherIconCode = weatherIconCode;
        this.temperature = temperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.visibilityRange = visibilityRange;
        this.windSpeed = windSpeed;
        this.rainVolume1Hour = rainVolume1Hour;
        this.rainVolume3Hours = rainVolume3Hours;
        this.cloudiness = cloudiness;
    }

    public String getWeatherName() {
        return weatherName;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getWeatherIconCode() {
        return weatherIconCode;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public Double getPressure() {
        return pressure;
    }

    public Double getHumidity() {
        return humidity;
    }

    public Integer getVisibilityRange() {
        return visibilityRange;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public Double getRainVolume1Hour() {
        return rainVolume1Hour;
    }

    public Double getRainVolume3Hours() {
        return rainVolume3Hours;
    }

    public Double getCloudiness() {
        return cloudiness;
    }
}

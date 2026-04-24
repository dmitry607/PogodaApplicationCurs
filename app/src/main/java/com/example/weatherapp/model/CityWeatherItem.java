package com.example.weatherapp.model;

public class CityWeatherItem {

    private final CityInfo cityInfo;
    private WeatherResponse weatherResponse;

    private boolean isLoading;
    private String errorMessage;

    public CityWeatherItem(CityInfo cityInfo) {
        this.cityInfo = cityInfo;
        this.isLoading = true;
    }

    public CityWeatherItem(CityInfo cityInfo, WeatherResponse weatherResponse) {
        this.cityInfo = cityInfo;
        this.weatherResponse = weatherResponse;
        this.isLoading = false;
    }

    public CityInfo getCityInfo() { return cityInfo; }
    public WeatherResponse getWeatherResponse() { return weatherResponse; }
    public boolean isLoading() { return isLoading; }
    public String getErrorMessage() { return errorMessage; }

    public void setWeatherResponse(WeatherResponse weatherResponse) {
        this.weatherResponse = weatherResponse;
        this.isLoading = false;
    }

    public void setError(String errorMessage) {
        this.errorMessage = errorMessage;
        this.isLoading = false;
    }

    public boolean hasError() {
        return errorMessage != null && !errorMessage.isEmpty();
    }
}

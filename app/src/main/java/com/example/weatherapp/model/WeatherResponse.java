package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("timezone")
    private String timezone;

    @SerializedName("current_weather")
    private CurrentWeather currentWeather;

    @SerializedName("daily")
    private DailyWeather daily;

    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getTimezone() { return timezone; }
    public CurrentWeather getCurrentWeather() { return currentWeather; }
    public DailyWeather getDaily() { return daily; }
}
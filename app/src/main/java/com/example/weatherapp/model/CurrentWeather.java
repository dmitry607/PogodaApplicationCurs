package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;
public class CurrentWeather {

    @SerializedName("temperature")
    private double temperature;

    @SerializedName("windspeed")
    private double windSpeed;

    @SerializedName("winddirection")
    private double windDirection;

    @SerializedName("weathercode")
    private int weatherCode;

    @SerializedName("is_day")
    private int isDay;

    @SerializedName("time")
    private String time;

    public double getTemperature() { return temperature; }
    public double getWindSpeed() { return windSpeed; }
    public double getWindDirection() { return windDirection; }
    public int getWeatherCode() { return weatherCode; }
    public boolean isDay() { return isDay == 1; }
    public String getTime() { return time; }
}
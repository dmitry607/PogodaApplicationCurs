package com.example.weatherapp.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DailyWeather {

    @SerializedName("time")
    private List<String> time;

    @SerializedName("temperature_2m_max")
    private List<Double> temperatureMax;

    @SerializedName("temperature_2m_min")
    private List<Double> temperatureMin;

    @SerializedName("weathercode")
    private List<Integer> weatherCode;

    @SerializedName("wind_speed_10m_max")
    private List<Double> windSpeedMax;

    public List<String> getTime() { return time; }
    public List<Double> getTemperatureMax() { return temperatureMax; }
    public List<Double> getTemperatureMin() { return temperatureMin; }
    public List<Integer> getWeatherCode() { return weatherCode; }
    public List<Double> getWindSpeedMax() { return windSpeedMax; }

    public double getSafeMax(int index) {
        if (temperatureMax != null && index < temperatureMax.size()) {
            Double val = temperatureMax.get(index);
            return val != null ? val : 0.0;
        }
        return 0.0;
    }

    public double getSafeMin(int index) {
        if (temperatureMin != null && index < temperatureMin.size()) {
            Double val = temperatureMin.get(index);
            return val != null ? val : 0.0;
        }
        return 0.0;
    }

    public int getSafeWeatherCode(int index) {
        if (weatherCode != null && index < weatherCode.size()) {
            Integer val = weatherCode.get(index);
            return val != null ? val : 0;
        }
        return 0;
    }

    public int getDaysCount() {
        return time != null ? time.size() : 0;
    }
}

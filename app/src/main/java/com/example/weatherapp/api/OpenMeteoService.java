package com.example.weatherapp.api;

import com.example.weatherapp.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenMeteoService {
    @GET("v1/forecast")
    Call<WeatherResponse> getWeather(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("current_weather") boolean currentWeather,
            @Query("daily") String daily,
            @Query("timezone") String timezone,
            @Query("forecast_days") int forecastDays
    );
}
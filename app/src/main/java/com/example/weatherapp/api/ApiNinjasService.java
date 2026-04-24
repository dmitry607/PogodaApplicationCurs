package com.example.weatherapp.api;

import com.example.weatherapp.model.CityInfo;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;


public interface ApiNinjasService {

    @GET("v1/city")
    Call<List<CityInfo>> getCityCoordinates(
            @Header("X-Api-Key") String apiKey,
            @Query("name") String cityName
    );
}

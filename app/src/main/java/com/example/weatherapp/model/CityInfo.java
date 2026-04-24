package com.example.weatherapp.model;
import com.google.gson.annotations.SerializedName;
public class CityInfo {

    @SerializedName("name")
    private String name;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("country")
    private String country;

    @SerializedName("population")
    private long population;

    public CityInfo() {}

    public String getName() { return name; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getCountry() { return country; }
    public long getPopulation() { return population; }

    @Override
    public String toString() {
        return name + " (" + country + ") [" + latitude + ", " + longitude + "]";
    }
}

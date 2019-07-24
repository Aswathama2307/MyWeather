package com.example.myweather.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("weather")
    Call<WeatherResult> getWeatherReport(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String appid);
}
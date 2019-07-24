package com.example.myweather.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherForecastApiService
{
    @GET("forecast")
    Call<WeatherForecastResult> getWeatherReport(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String appid);
}

package com.example.myweather.Retrofit;

import com.example.myweather.Model.WeatherForecastResult;
import com.example.myweather.Model.WeatherResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap
{
    @GET("weather")
    Call<WeatherResult> getWeatherByLatLng(@Query("lat") String lat,
                                           @Query("lon") String lng,
                                           @Query("appid") String appid,
                                           @Query("units") String unit);

    @GET("forecast")
    Call<WeatherForecastResult> getForecastWeatherByLatLng(@Query("lat") String lat,
                                                           @Query("lon") String lng,
                                                           @Query("appid") String appid,
                                                           @Query("units") String unit);
}
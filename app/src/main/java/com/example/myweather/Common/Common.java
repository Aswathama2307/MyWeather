package com.example.myweather.Common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common
{
    public static final String base_url = "http://api.openweathermap.org/data/2.5/";
    public static final String img_path = "https://openweathermap.org/img/wn/";
    public static final String API_ID = "b7da4e0c9e4b6807579c9967039c46fd";
    public static Location current_location= null;

    public static String convertUnixToDate(long dt)
    {
        Date date = new Date(dt*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd EEE MM yyyy");
        String formatted = sdf.format(date);
        return formatted;
    }

    public static String convertUnixToHour(long dt)
    {
        Date date = new Date(dt*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String formatted = sdf.format(date);
        return formatted;
    }
}

package com.example.myweather.Model;

import com.google.gson.annotations.SerializedName;

public class Coord {
    @SerializedName("lon")
    public float lon;
    @SerializedName("lat")
    public float lat;
}

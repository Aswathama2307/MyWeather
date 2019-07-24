package com.example.myweather.Model;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

public class Clouds {

    @SerializedName("all")
    @Expose
    private Integer all;

    public Integer getAll() {
        return all;
    }

    public void setAll(Integer all) {
        this.all = all;
    }

}

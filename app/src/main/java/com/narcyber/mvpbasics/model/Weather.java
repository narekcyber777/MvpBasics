package com.narcyber.mvpbasics.model;

import com.google.gson.annotations.SerializedName;

public class Weather {


    @SerializedName("main")
    private WeatherMain main;

    public WeatherMain getMain() {
        return main;
    }

    public void setMain(WeatherMain main) {
        this.main = main;
    }
}

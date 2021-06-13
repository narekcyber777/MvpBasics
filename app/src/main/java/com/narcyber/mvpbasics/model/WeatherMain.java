package com.narcyber.mvpbasics.model;

import com.google.gson.annotations.SerializedName;

public class WeatherMain {
    @SerializedName("temp")
    String temp;
    public String getTemp() {
        return temp;
    }
    public void setTemp(String temp) {
        this.temp = temp;
    }
}

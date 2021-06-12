package com.narcyber.mvpbasics.network;

import com.narcyber.mvpbasics.model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiWeatherInterface {
    @GET("weather?appid=3f2076070ae8fbcd02fac4b9569c6fb3&units=metric")
    Call<Weather> getWeatherData(@Query("q") String name);
}

package com.narcyber.mvpbasics.network;

import com.narcyber.mvpbasics.model.Weather;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiWeatherInterface {
    @GET("weather?units=metric")
    Observable<Weather> getWeatherData(@Query("appid") String id, @Query("q") String name);
}

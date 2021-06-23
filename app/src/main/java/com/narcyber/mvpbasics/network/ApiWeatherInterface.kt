package com.narcyber.mvpbasics.network

import com.narcyber.mvpbasics.model.Weather
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiWeatherInterface {
    @GET("weather?units=metric")
    fun getWeatherData(
        @Query("appid") id: String,
        @Query("q") name: String
    ): Observable<Weather>
}
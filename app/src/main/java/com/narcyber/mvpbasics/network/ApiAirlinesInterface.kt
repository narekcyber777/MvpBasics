package com.narcyber.mvpbasics.network

import com.narcyber.mvpbasics.model.AirlinesContainer
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiAirlinesInterface {
    @GET("passenger?")
    fun getAirlineData(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Single<AirlinesContainer>
}
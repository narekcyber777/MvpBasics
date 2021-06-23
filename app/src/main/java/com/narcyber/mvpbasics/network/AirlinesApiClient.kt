package com.narcyber.mvpbasics.network

import com.narcyber.mvpbasics.helper.ConstantHelper
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object AirlinesApiClient {

    val client: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(ConstantHelper.BASE_AIRLINES_URL)
            .client(UnsafeOkHttpClient.getUnsafeOkHttpClient().build())
            .build()
    }


}
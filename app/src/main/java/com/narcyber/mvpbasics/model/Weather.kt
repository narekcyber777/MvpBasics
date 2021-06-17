package com.narcyber.mvpbasics.model

import com.google.gson.annotations.SerializedName

data class Weather(@SerializedName("main") var main: WeatherMain)
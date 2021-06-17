package com.narcyber.mvpbasics.model

import com.google.gson.annotations.SerializedName

data class WeatherMain(@SerializedName("temp") var temp: String)
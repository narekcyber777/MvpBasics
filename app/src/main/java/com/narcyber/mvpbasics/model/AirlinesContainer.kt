package com.narcyber.mvpbasics.model

import com.google.gson.annotations.SerializedName

data class AirlinesContainer(@SerializedName("data") var data: MutableList<AirlinesContent>)

data class AirlinesContent(
    @SerializedName("_id") var id: String,
    @SerializedName("name") var name: String,
)


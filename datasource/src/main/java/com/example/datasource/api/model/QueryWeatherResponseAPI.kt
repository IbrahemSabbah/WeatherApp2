package com.example.datasource.api.model

import com.google.gson.annotations.SerializedName

data class QueryWeatherResponseAPI(
    @SerializedName("data")
    val responseData: Data
)
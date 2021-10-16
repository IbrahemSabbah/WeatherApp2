package com.example.datasource.api.model

data class CurrentConditionApiEntity(
    val FeelsLikeC: String?="",
    val humidity: String?="",
    val temp_C: String?="",
    val weatherDesc: List<WeatherDesc>,
    val weatherIconUrl: List<WeatherIconUrl>,
    val windspeedKmph: String?=""
)
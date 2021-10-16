package com.example.datasource.api.model

data class CurrentCondition(
    val FeelsLikeC: String?="",
    val FeelsLikeF: String?="",
    val humidity: String?="",
    val observation_time: String?="",
    val temp_C: String?="",
    val temp_F: String?="",
    val weatherDesc: List<WeatherDesc>,
    val weatherIconUrl: List<WeatherIconUrl>,
    val windspeedKmph: String?="",
    val windspeedMiles: String?=""
)
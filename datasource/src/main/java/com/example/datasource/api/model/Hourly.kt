package com.example.datasource.api.model

data class Hourly(
    val FeelsLikeC: String,
    val FeelsLikeF: String,
    val humidity: String,
    val tempC: String,
    val tempF: String,
    val time: String,
    val weatherDesc: List<WeatherDesc>,
    val weatherIconUrl: List<WeatherIconUrl>,
    val windspeedKmph: String,
    val windspeedMiles: String
)
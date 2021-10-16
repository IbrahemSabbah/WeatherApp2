package com.example.domain.model

data class WeatherCityEntityDomain(
    val cityName: String,
    val temp: String ,
    val weatherIcon: String ,
    val weatherDescription: String ,
    val windsSpeed: String ,
    val humidity: String ,
    val feelsLike: String
)


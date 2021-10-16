package com.example.datasource.cache.entities

data class WeatherCityEntityCache(
    val cityId: String = "",
    val cityName: String = "",
    val temp: String = "",
    val weatherIcon: String = "",
    val weatherDescription: String = "",
    val windsSpeed: String = "",
    val humidity: String = "",
    val feelsLike: String = ""
)


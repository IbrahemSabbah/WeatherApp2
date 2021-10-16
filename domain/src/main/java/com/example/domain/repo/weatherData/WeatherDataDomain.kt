package com.example.domain.repo.weatherData

import androidx.paging.PagingSource
import com.example.datasource.cache.entities.WeatherCityEntityCache

interface WeatherDataDomain {

    fun getWeatherData():PagingSource<Int,WeatherCityEntityCache>
}
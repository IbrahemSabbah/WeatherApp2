package com.example.domain.repo.weatherData

import androidx.paging.PagingSource
import com.example.datasource.cache.dao.CityDAO
import com.example.datasource.cache.entities.WeatherCityEntityCache
import javax.inject.Inject

class WeatherDataDomainRepo @Inject constructor(private val cityDAO: CityDAO) : WeatherDataDomain {
    override fun getWeatherData(): PagingSource<Int, WeatherCityEntityCache> {
        return cityDAO.getWeatherCity()
    }
}
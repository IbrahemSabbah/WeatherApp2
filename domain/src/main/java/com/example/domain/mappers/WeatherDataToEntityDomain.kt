package com.example.domain.mappers

import com.example.datasource.api.model.RequestApiEntity
import com.example.datasource.cache.entities.CityEntityCache
import com.example.datasource.cache.entities.WeatherCityEntityCache
import com.example.domain.model.EntityMapper
import com.example.domain.model.WeatherCityEntityDomain

class WeatherDataToEntityDomain : EntityMapper<WeatherCityEntityCache, WeatherCityEntityDomain> {
    override fun mapFromEntity(entity: WeatherCityEntityCache): WeatherCityEntityDomain {
        return WeatherCityEntityDomain(
            cityName = entity.cityName,
            cityColor = entity.cityColor,
            temp = entity.temp,
            weatherIcon = entity.weatherIcon,
            windsSpeed = entity.windsSpeed,
            humidity = entity.humidity,
            feelsLike = entity.feelsLike
        )
    }

    override fun mapToEntity(domainModel: WeatherCityEntityDomain): WeatherCityEntityCache {
        TODO("Not yet implemented")
    }

}
package com.example.domain.mappers

import com.example.datasource.api.model.CurrentConditionApiEntity

import com.example.datasource.cache.entities.ConditionsEntityCache
import com.example.domain.model.EntityMapper

class ConditionApiToEntity(private val cityId: Long) :
    EntityMapper<CurrentConditionApiEntity, ConditionsEntityCache> {


    override fun mapFromEntity(entity: CurrentConditionApiEntity): ConditionsEntityCache {
        return ConditionsEntityCache(
            cityId = cityId,
            temp = entity.temp_C ?: "",
            weatherIcon = entity.weatherIconUrl.first().value ?: "",
            weatherDescription = entity.weatherDesc.first().value ?: "",
            windsSpeed = entity.windspeedKmph ?: "",
            humidity = entity.humidity ?: "",
            feelsLike = entity.FeelsLikeC ?: ""

        )
    }

    override fun mapToEntity(domainModel: ConditionsEntityCache): CurrentConditionApiEntity {
        TODO("Not yet implemented")
    }
}
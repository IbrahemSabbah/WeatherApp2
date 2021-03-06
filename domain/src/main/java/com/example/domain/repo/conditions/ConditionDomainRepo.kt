package com.example.domain.repo.conditions

import com.example.datasource.api.model.CurrentConditionApiEntity
import com.example.datasource.cache.dao.ConditionsDAO
import com.example.domain.mappers.ConditionApiToEntity
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class ConditionDomainRepo @Inject constructor(private val conditionsDAO: ConditionsDAO):ConditionDomain {
    override suspend fun addNewCondition(
        cityId: Long,
        currentConditionApiEntity: CurrentConditionApiEntity
    ) {
        coroutineScope {
            conditionsDAO.insert(ConditionApiToEntity(cityId).mapFromEntity(currentConditionApiEntity))
        }
    }

    override suspend fun updateCondition(
        cityId: Long,
        currentConditionApiEntity: CurrentConditionApiEntity
    ) {
        coroutineScope {
            val conditionEntityCache=ConditionApiToEntity(cityId).mapFromEntity(currentConditionApiEntity)

            conditionsDAO.update(
                cityId = cityId,
                temp = conditionEntityCache.temp,
                weatherIcon = conditionEntityCache.weatherIcon,
                weatherDescription = conditionEntityCache.weatherDescription,
                windsSpeed = conditionEntityCache.windsSpeed,
                humidity = conditionEntityCache.humidity,
                feelsLike = conditionEntityCache.feelsLike
            )
        }
    }

}
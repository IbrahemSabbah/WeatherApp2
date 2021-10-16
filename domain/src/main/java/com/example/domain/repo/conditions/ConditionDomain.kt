package com.example.domain.repo.conditions

import com.example.datasource.api.model.CurrentConditionApiEntity

interface ConditionDomain {

    suspend fun addNewCondition(cityId: Long, currentConditionApiEntity: CurrentConditionApiEntity)
}
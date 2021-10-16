package com.example.domain.useCase.citySearch

import com.example.domain.model.CityNameDomainEntity
import kotlinx.coroutines.flow.Flow

interface CitySearch {

    suspend fun search(query: String):List<CityNameDomainEntity>
}
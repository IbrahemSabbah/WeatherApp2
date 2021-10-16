package com.example.domain.useCase.citySearch

import com.example.datasource.api.ApiHelper
import com.example.datasource.api.model.DataState
import com.example.domain.mappers.CitySearchToCityDomain
import com.example.domain.model.CityNameDomainEntity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class CitySearchUseCase @Inject constructor(private val apiHelper: ApiHelper) : CitySearch {

    private val _state = MutableSharedFlow<List<CityNameDomainEntity>>()
    val responseFlow: SharedFlow<List<CityNameDomainEntity>> = _state
    private val mapper = CitySearchToCityDomain()

    override suspend fun search(query: String): List<CityNameDomainEntity> {
        return coroutineScope {
            val result = apiHelper.getCity(query)

            return@coroutineScope if (result is DataState.Success) {
                val list = result.data.search_api?.result?.map {
                    mapper.mapFromEntity(it)
                }
                list ?: emptyList<CityNameDomainEntity>()
            } else emptyList()
        }
    }


}
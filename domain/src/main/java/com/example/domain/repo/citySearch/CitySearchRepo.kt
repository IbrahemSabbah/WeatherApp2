package com.example.domain.repo.citySearch

import com.example.datasource.api.ApiHelper
import com.example.datasource.api.model.DataState
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class CitySearchRepo @Inject constructor(private val appHelper: ApiHelper): CitySearch {
    override suspend fun citySearch(cityName: String): DataState<String> {
        return coroutineScope {
           return@coroutineScope appHelper.weatherQueryByCity(cityName)
        }
    }
}
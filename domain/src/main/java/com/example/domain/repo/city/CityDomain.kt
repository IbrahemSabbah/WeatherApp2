package com.example.domain.repo.city

import com.example.datasource.api.model.DataState
import com.example.datasource.api.model.QueryWeatherResponseAPI
import com.example.datasource.api.model.RequestApiEntity
import com.example.datasource.cache.entities.CityEntityCache

interface CityDomain {

    suspend fun weatherQuery(cityName: String): DataState<QueryWeatherResponseAPI>
    suspend fun addNewCity(requestApiEntity: RequestApiEntity): Long?
    suspend fun isCityExist(cityName: String): Boolean
    suspend fun getCityByName(cityName: String): CityEntityCache
    suspend fun getCityNameList():Array<String>
}
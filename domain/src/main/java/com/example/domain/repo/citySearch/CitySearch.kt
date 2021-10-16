package com.example.domain.repo.citySearch

import com.example.datasource.api.model.DataState
import com.example.datasource.api.model.QueryWeatherResponseAPI

interface CitySearch {

    suspend fun citySearch(cityName:String): DataState<QueryWeatherResponseAPI>
}
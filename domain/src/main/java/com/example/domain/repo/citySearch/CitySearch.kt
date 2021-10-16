package com.example.domain.repo.citySearch

import com.example.datasource.api.model.DataState

interface CitySearch {

    suspend fun citySearch(cityName:String): DataState<String>
}
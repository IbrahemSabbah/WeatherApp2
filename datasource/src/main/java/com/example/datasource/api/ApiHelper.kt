package com.example.datasource.api

import com.example.datasource.api.model.DataState
import com.example.datasource.api.model.QueryWeatherResponseAPI

interface ApiHelper {

    suspend fun weatherQueryByCity(cityName: String): DataState<QueryWeatherResponseAPI>
}
package com.example.datasource.api

import com.example.datasource.api.model.DataState

interface ApiHelper {

    suspend fun weatherQueryByCity(cityName: String): DataState<String>
}
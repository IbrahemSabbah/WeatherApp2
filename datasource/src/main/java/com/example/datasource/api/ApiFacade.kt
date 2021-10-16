package com.example.datasource.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface ApiFacade {

    @GET("weather.ashx")
    suspend fun queryByCityName(@QueryMap queryMap: Map<String,String>):Response<String>
}
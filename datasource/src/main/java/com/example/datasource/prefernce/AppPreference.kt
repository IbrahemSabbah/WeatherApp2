package com.example.datasource.prefernce

interface AppPreference {

    suspend fun isDefaultCityFetched(): Boolean
    suspend fun isDefaultCityFetched(value: Boolean)

}
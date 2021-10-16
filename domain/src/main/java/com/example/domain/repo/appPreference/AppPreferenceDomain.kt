package com.example.domain.repo.appPreference

interface AppPreferenceDomain {

    suspend fun isDefaultCityFetched(): Boolean
    suspend fun isDefaultCityFetched(value: Boolean)
}
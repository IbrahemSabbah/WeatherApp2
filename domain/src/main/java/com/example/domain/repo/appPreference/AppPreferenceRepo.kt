package com.example.domain.repo.appPreference

import com.example.datasource.prefernce.AppPreference
import javax.inject.Inject

class AppPreferenceRepo @Inject constructor(private val appPreference: AppPreference) :
    AppPreferenceDomain {
    override suspend fun isDefaultCityFetched(): Boolean {
        return appPreference.isDefaultCityFetched()
    }

    override suspend fun isDefaultCityFetched(value: Boolean) {
        appPreference.isDefaultCityFetched(value)
    }
}
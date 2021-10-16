package com.example.datasource.prefernce

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppPreferenceImp @Inject constructor(@ApplicationContext context: Context) : AppPreference {
    private val IS_DEFAULT_CITY_FETCHED = "isDefaultCityFetched"
    private val masterKeyAlias: String by lazy {
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    }
    val sharedPreferences: SharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            "WeatherShard",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override suspend fun isDefaultCityFetched(): Boolean =
        sharedPreferences.getBoolean(IS_DEFAULT_CITY_FETCHED, false)


    override suspend fun isDefaultCityFetched(value: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(IS_DEFAULT_CITY_FETCHED, value)
            apply()
        }
    }


}


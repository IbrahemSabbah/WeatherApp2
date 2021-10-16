package com.example.datasource.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.datasource.cache.dao.CityDAO
import com.example.datasource.cache.dao.ConditionsDAO
import com.example.datasource.cache.entities.CityEntityCache
import com.example.datasource.cache.entities.ConditionsEntityCache


@Database(
    entities = [CityEntityCache::class, ConditionsEntityCache::class],
    version = 1
)
abstract class WeatherDB : RoomDatabase() {

    abstract fun cityDAO():CityDAO
    abstract fun conditionsDAO():ConditionsDAO
    companion object {
        const val DB_NAME = "weather_db"
    }
}
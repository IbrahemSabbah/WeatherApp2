package com.example.datasource.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.datasource.cache.entities.Account


@Database(
    entities = [Account::class],
    version = 1
)
abstract class WeatherDB:RoomDatabase() {

    companion object{
        const val DB_NAME="weather_db"
    }
}
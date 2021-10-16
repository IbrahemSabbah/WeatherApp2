package com.example.datasource.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.datasource.cache.WeatherDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CacheModule {

    @Singleton
    @Provides
    fun provideWeatherDB(@ApplicationContext context: Context): RoomDatabase {
        return Room.databaseBuilder(
            context,
            WeatherDB::class.java,
            WeatherDB.DB_NAME
        ).build()
    }

}
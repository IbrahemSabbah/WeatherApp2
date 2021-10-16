package com.example.datasource.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "conditions",
    foreignKeys = [ForeignKey(
        entity = CityEntityCache::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("cityId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class ConditionsEntityCache(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val cityId: Long,
    val temp: String,
    val weatherIcon: String,
    val weatherDescription: String,
    val windsSpeed: String,
    val humidity:String,
    val feelsLike:String
)
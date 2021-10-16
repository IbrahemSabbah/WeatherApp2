package com.example.datasource.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class CityEntityCache(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val name: String
    )
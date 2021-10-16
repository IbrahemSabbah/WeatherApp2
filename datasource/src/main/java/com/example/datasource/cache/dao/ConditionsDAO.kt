package com.example.datasource.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.datasource.cache.entities.ConditionsEntityCache


@Dao
interface ConditionsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(conditionsEntityCache: ConditionsEntityCache)
}
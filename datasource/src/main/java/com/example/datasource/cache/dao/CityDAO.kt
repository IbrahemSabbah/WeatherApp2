package com.example.datasource.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.datasource.cache.entities.CityEntityCache


@Dao
interface CityDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cityEntityCache: CityEntityCache): Long

    @Query(
        """
       SELECT EXISTS ( select name from city where name like '%' || :cityName || '%')
    """
    )
    fun isCityExist(cityName: String):Boolean
}
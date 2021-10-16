package com.example.datasource.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.datasource.cache.entities.ConditionsEntityCache


@Dao
interface ConditionsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(conditionsEntityCache: ConditionsEntityCache)


    @Query(
        """
        Update conditions
        set
        `temp` =:temp,
        weatherIcon=:weatherIcon,
        weatherDescription=:weatherDescription,
        windsSpeed=:windsSpeed,
        humidity=:humidity,
        feelsLike=:feelsLike
        
        where cityId=:cityId
    """
    )
    fun update(
        cityId:Long,
        temp: String,
        weatherIcon: String,
        weatherDescription: String,
        windsSpeed: String,
        humidity: String,
        feelsLike: String
    )
}
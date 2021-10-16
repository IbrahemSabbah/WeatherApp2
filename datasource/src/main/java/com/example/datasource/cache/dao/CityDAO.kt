package com.example.datasource.cache.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.datasource.cache.entities.CityEntityCache
import com.example.datasource.cache.entities.WeatherCityEntityCache


@Dao
interface CityDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cityEntityCache: CityEntityCache): Long

    @Query(
        """
       SELECT EXISTS ( select name from city where name like '%' || :cityName || '%')
    """
    )
    fun isCityExist(cityName: String): Boolean


    @Query(
        """
        Select 
        city.id 'cityId',
        city.name 'cityName',
        city.color 'cityColor',
        conditions.`temp`   'temp',
        conditions.weatherIcon 'weatherIcon',
        conditions.weatherDescription 'weatherDescription',
        conditions.windsSpeed 'windsSpeed',
        conditions.humidity 'humidity',
        conditions.feelsLike 'feelsLike'
        
        from city
        
        inner join conditions on conditions.cityId=city.id
    """
    )

    fun getWeatherCity(): PagingSource<Int, WeatherCityEntityCache>
}
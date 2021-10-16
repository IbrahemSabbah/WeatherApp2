package com.example.datasource.cache.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.datasource.cache.entities.CityEntityCache
import com.example.datasource.cache.entities.ConditionsEntityCache
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
       select * from city where name like '%' || :cityName || '%'
    """
    )
    fun getCityByName(cityName: String): CityEntityCache


    @Query(
        """
       select name from city
    """
    )
    fun getListCityName(): List<String>


    @Query(
        """
        Select 
        city.id 'cityId',
        city.name 'cityName',
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
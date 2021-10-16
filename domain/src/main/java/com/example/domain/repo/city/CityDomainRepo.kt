package com.example.domain.repo.city

import com.example.datasource.api.ApiHelper
import com.example.datasource.api.model.DataState
import com.example.datasource.api.model.QueryWeatherResponseAPI
import com.example.datasource.api.model.RequestApiEntity
import com.example.datasource.cache.dao.CityDAO
import com.example.datasource.cache.entities.CityEntityCache
import com.example.domain.mappers.CityApiToEntity
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class CityDomainRepo @Inject constructor(
    private val appHelper: ApiHelper,
    private val cityDAO: CityDAO
) : CityDomain {
    override suspend fun weatherQuery(cityName: String): DataState<QueryWeatherResponseAPI> {
        return coroutineScope {

            return@coroutineScope appHelper.weatherQueryByCity(cityName)
        }
    }

    override suspend fun addNewCity(requestApiEntity: RequestApiEntity): Long? {
        return coroutineScope {
            cityDAO.insert(CityApiToEntity().mapFromEntity(requestApiEntity))
        }
    }

    override suspend fun isCityExist(cityName: String): Boolean {
        return coroutineScope {
            cityDAO.isCityExist(cityName)
        }
    }

    override suspend fun getCityByName(cityName: String): CityEntityCache {
        return coroutineScope {
            cityDAO.getCityByName(cityName)
        }
    }

    override suspend fun getCityNameList(): Array<String> {
        return coroutineScope {
            cityDAO.getListCityName().toTypedArray()
        }
    }


}
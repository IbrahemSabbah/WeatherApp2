package com.example.domain.repo.city

import com.example.datasource.api.ApiHelper
import com.example.datasource.api.model.DataState
import com.example.datasource.api.model.QueryWeatherResponseAPI
import com.example.datasource.api.model.RequestApiEntity
import com.example.datasource.cache.dao.CityDAO
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
            if (!isCityExist(requestApiEntity.query))
                cityDAO.insert(CityApiToEntity().mapFromEntity(requestApiEntity))
            else null
        }
    }

    override suspend fun isCityExist(cityName: String): Boolean {
        return coroutineScope {
            cityDAO.isCityExist(cityName)
        }
    }


}
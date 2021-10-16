package com.example.datasource.api

import com.example.datasource.api.model.CitySearchApiEntity
import com.example.datasource.api.model.Data
import com.example.datasource.api.model.DataState
import com.example.datasource.api.model.QueryWeatherResponseAPI
import com.example.datasource.helper.reportError
import kotlinx.coroutines.coroutineScope
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImp @Inject constructor(private val apiFacade: ApiFacade) : ApiHelper {


    override suspend fun weatherQueryByCity(cityName: String): DataState<QueryWeatherResponseAPI> {
        return coroutineScope {
            val queryMap = HashMap<String, String>()
            queryMap["q"] = cityName
            queryMap["num_of_days"] = "5"

            val request: Response<QueryWeatherResponseAPI> = apiFacade.queryByCityName(queryMap)

            return@coroutineScope if (request.isSuccessful) {
                request.body()?.let {
                    DataState.Success(it)

                } ?: DataState.Error(Exception("No Body"))
            } else DataState.Error(request.reportError())

        }

    }

    override suspend fun getCity(query: String): DataState<CitySearchApiEntity> {
        return coroutineScope {
            val queryMap = HashMap<String, String>()
            queryMap["query"] = query
            queryMap["num_of_results"] = "5"

            val request: Response<CitySearchApiEntity> = apiFacade.getCityName(queryMap)

            return@coroutineScope if (request.isSuccessful) {
                request.body()?.let {
                    DataState.Success(it)

                } ?: DataState.Error(Exception("No Body"))
            } else DataState.Error(request.reportError())

        }
    }
}
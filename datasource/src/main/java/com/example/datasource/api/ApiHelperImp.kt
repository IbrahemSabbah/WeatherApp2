package com.example.datasource.api

import com.example.datasource.api.model.DataState
import com.example.datasource.helper.reportError
import kotlinx.coroutines.coroutineScope
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImp @Inject constructor(private val apiFacade: ApiFacade) : ApiHelper {


    override suspend fun weatherQueryByCity(cityName: String): DataState<String> {
        return coroutineScope {
            val queryMap = HashMap<String, String>()
            queryMap["q"] = cityName
            queryMap["num_of_days"] = "5"

            val request: Response<String> = apiFacade.queryByCityName(queryMap)

           return@coroutineScope if (request.isSuccessful) {
                DataState.Success("")
            } else DataState.Error(request.reportError())

        }

    }
}
package com.example.domain.useCase

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.datasource.api.model.DataState
import com.example.datasource.prefernce.AppPreference
import com.example.domain.repo.city.CityDomain
import com.example.domain.repo.conditions.ConditionDomainRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltWorker
class FetchCityWeatherWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    private val TAG = this.javaClass.canonicalName

    @Inject
    lateinit var cityDomainRepo: CityDomain

    @Inject
    lateinit var conditionsRepo: ConditionDomainRepo


    override suspend fun doWork(): Result {

        return coroutineScope {
            return@coroutineScope try {


                val cityName = getCityName()
                val response = withContext(Dispatchers.IO) {
                    cityDomainRepo.weatherQuery(cityName)
                }

                var isFail = response is DataState.Error


                if (isFail)
                    Result.failure()
                else {
                        val cityDetails=response as DataState.Success
                        val cityId =
                            cityDomainRepo.addNewCity(cityDetails.data.responseData.request.first())
                        cityId?.run {
                            conditionsRepo.addNewCondition(
                                this,
                                cityDetails.data.responseData.current_condition.first()
                            )
                        }
                    Result.success()

                }
            } catch (e: Exception) {
                Log.d(TAG, e.message ?: "")
                Result.failure()
            }
        }
    }

    private fun getCityName(): String {
        return inputData.getString(CITY_NAME) ?: ""
    }

    companion object {

        const val WORKER_TAG = "FetchCity"
        const val CITY_NAME = "cityName"

        fun getWorkerData(cityName: String): Data.Builder {
            val inputData = Data.Builder()
            inputData.putString(CITY_NAME, cityName)
            return inputData
        }

        fun startWorker(context: Context, cityName: String) {
            val citySearchWorker = OneTimeWorkRequestBuilder<FetchCityWeatherWorker>()
            citySearchWorker.setInputData(getWorkerData(cityName).build())

            WorkManager.getInstance(context).enqueueUniqueWork(
                WORKER_TAG,
                ExistingWorkPolicy.KEEP,
                citySearchWorker.build()
            )
        }
    }
}
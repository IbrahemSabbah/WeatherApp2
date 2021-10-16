package com.example.domain.useCase

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.datasource.api.model.DataState
import com.example.datasource.prefernce.AppPreference
import com.example.domain.repo.citySearch.CityDomain
import com.example.domain.repo.conditions.ConditionDomainRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

@HiltWorker
class CitySearchWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    private val TAG = this.javaClass.canonicalName

    @Inject
    lateinit var cityDomainRepo: CityDomain

    @Inject
    lateinit var conditionsRepo: ConditionDomainRepo

    @Inject
    lateinit var appPreference: AppPreference

    override suspend fun doWork(): Result {

        return coroutineScope {
            return@coroutineScope try {
                val cities = getCities()

                val asyncTasks = cities!!.map {
                    async { cityDomainRepo.weatherQuery(it) }
                }
                val response = asyncTasks.awaitAll()

                var isFail = response.any { it is DataState.Error }

                if (isFail)
                    Result.failure()
                else {
                    response.map { it as DataState.Success }.forEach {
                        val cityId =
                            cityDomainRepo.addNewCity(it.data.responseData.request.first())
                        cityId?.run {
                            conditionsRepo.addNewCondition(
                                this,
                                it.data.responseData.current_condition.first()
                            )
                        } ?: Log.d(TAG, "City Already Exist")
                    }
                    appPreference.isDefaultCityFetched(true)
                    Result.success()

                }
            } catch (e: Exception) {
                Log.d(TAG, e.message ?: "")
                Result.failure()
            }
        }
    }

    private fun getCities(): Array<out String>? {
        return inputData.getStringArray(CityArray)
    }

    companion object {

        const val WORKER_TAG = "CitySearchWorker"
        const val CityArray = "cityArray"

        fun getWorkerData(cities: Array<String>): Data.Builder {
            val inputData = Data.Builder()
            inputData.putStringArray(CityArray, cities)
            return inputData
        }

        fun startWorker(context: Context, cities: Array<String>) {
            val citySearchWorker = OneTimeWorkRequestBuilder<CitySearchWorker>()
            citySearchWorker.setInputData(getWorkerData(cities).build())

            WorkManager.getInstance(context).enqueueUniqueWork(
                WORKER_TAG,
                ExistingWorkPolicy.KEEP,
                citySearchWorker.build()
            )
        }
    }
}
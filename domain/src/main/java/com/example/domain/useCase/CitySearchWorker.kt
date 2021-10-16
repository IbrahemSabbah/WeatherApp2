package com.example.domain.useCase

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.example.datasource.api.model.DataState
import com.example.domain.repo.citySearch.CitySearch
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
    lateinit var citySearchRepo: CitySearch


    override suspend fun doWork(): Result {

       return coroutineScope {
            return@coroutineScope try {
                val cities = getCities()

                val asyncTasks = cities!!.map {
                    async { citySearchRepo.citySearch(it) }
                }
                val response = asyncTasks.awaitAll()

                var isFail = response.any { it is DataState.Error }

                if (isFail)
                    Result.failure()
                else
                    Result.success()
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
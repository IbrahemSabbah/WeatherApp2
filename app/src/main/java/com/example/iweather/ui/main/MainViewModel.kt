package com.example.iweather.ui.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.domain.mappers.WeatherDataToEntityDomain
import com.example.domain.model.WeatherCityEntityDomain

import com.example.domain.repo.appPreference.AppPreferenceDomain
import com.example.domain.repo.weatherData.WeatherDataDomain
import com.example.domain.repo.weatherData.WeatherDataDomainRepo
import com.example.domain.useCase.CitySearchWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appPreferenceRepo: AppPreferenceDomain,
    private val weatherDataDomainRepo: WeatherDataDomain
) :
    ViewModel() {

    private val _state = MutableStateFlow<MainUiState>(MainUiState.None)
    val uiState: StateFlow<MainUiState> = _state
    private val weatherDataToEntityDomain = WeatherDataToEntityDomain()

    init {
        checkDefaultCity()
    }

    private fun checkDefaultCity() {
        viewModelScope.launch {
            if (!appPreferenceRepo.isDefaultCityFetched()) {
                _state.value = MainUiState.FetchingDefaultCity
                runCitySearchWorker()
            }
        }
    }

    fun getWeatherData(): Flow<PagingData<WeatherCityEntityDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { weatherDataDomainRepo.getWeatherData() }
        ).flow.map {
            it.map {weatherCityEntityCache->
                weatherDataToEntityDomain.mapFromEntity(weatherCityEntityCache)
            }
        }

    }

    private fun runCitySearchWorker() {
        viewModelScope.launch {
            CitySearchWorker.startWorker(
                context,
                arrayOf("amman", "irbid", "aqaba")
            )
            WorkManager.getInstance(context)
                .getWorkInfosForUniqueWorkLiveData(CitySearchWorker.WORKER_TAG)
                .asFlow().collectLatest {

                    when (it.first().state) {
                        WorkInfo.State.SUCCEEDED -> {
                            _state.value = MainUiState.SuccessFetch

                        }
                        WorkInfo.State.FAILED, WorkInfo.State.CANCELLED -> {
                            _state.value = MainUiState.FailFetchingDefaultCity

                        }
                        else -> {

                        }
                    }
                }
        }
    }


    sealed class MainUiState {
        object FetchingDefaultCity : MainUiState()
        object SuccessFetch : MainUiState()
        object FailFetchingDefaultCity : MainUiState()
        object None : MainUiState()
    }
}
package com.example.iweather.ui.main

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.domain.mappers.WeatherDataToEntityDomain
import com.example.domain.model.CityNameDomainEntity
import com.example.domain.model.WeatherCityEntityDomain

import com.example.domain.repo.appPreference.AppPreferenceDomain
import com.example.domain.repo.city.CityDomain
import com.example.domain.repo.city.CityDomainRepo
import com.example.domain.repo.weatherData.WeatherDataDomain
import com.example.domain.useCase.FetchCityWeatherWorker
import com.example.domain.useCase.GetDefaultCityWorker
import com.example.domain.useCase.UpdateWeatherCityWorker
import com.example.domain.useCase.citySearch.CitySearch
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appPreferenceRepo: AppPreferenceDomain,
    private val weatherDataDomainRepo: WeatherDataDomain,
    private val citySearchUseCase: CitySearch,
    private val cityDomainRepo: CityDomain
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
            it.map { weatherCityEntityCache ->
                weatherDataToEntityDomain.mapFromEntity(weatherCityEntityCache)
            }
        }

    }

    fun citySearch(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = MainUiState.SearchLoading(true)
            _state.value = MainUiState.NewSearchResult(citySearchUseCase.search(query))
        }
    }

    fun fetchCityWeather(cityName: String) {
        FetchCityWeatherWorker.startWorker(context, cityName)

        viewModelScope.launch {
            WorkManager.getInstance(context)
                .getWorkInfosForUniqueWorkLiveData(FetchCityWeatherWorker.WORKER_TAG)
                .asFlow().collectLatest {

                    when (it.first().state) {
                        WorkInfo.State.SUCCEEDED -> {
                            _state.value = MainUiState.SearchLoading(false)

                        }
                        WorkInfo.State.ENQUEUED, WorkInfo.State.RUNNING -> {
                            _state.value = MainUiState.SearchLoading(true)

                        }
                        WorkInfo.State.FAILED, WorkInfo.State.CANCELLED -> {

                        }
                        else -> {

                        }
                    }
                }
        }
    }

    fun updateAllCity() {
        viewModelScope.launch(Dispatchers.IO) {
            UpdateWeatherCityWorker.startWorker(context, cityDomainRepo.getCityNameList())
        }
    }

    private fun runCitySearchWorker() {
        viewModelScope.launch {
            GetDefaultCityWorker.startWorker(
                context,
                arrayOf("amman", "irbid", "aqaba")
            )
            WorkManager.getInstance(context)
                .getWorkInfosForUniqueWorkLiveData(GetDefaultCityWorker.WORKER_TAG)
                .asFlow().collectLatest {

                    when (it.first().state) {
                        WorkInfo.State.SUCCEEDED -> {
                            _state.value = MainUiState.SuccessFetch

                        }
                        WorkInfo.State.ENQUEUED, WorkInfo.State.RUNNING -> {

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
        data class SearchLoading(val isLoading: Boolean) : MainUiState()
        data class NewSearchResult(val list: List<CityNameDomainEntity>) : MainUiState()
        object FailFetchingDefaultCity : MainUiState()
        object None : MainUiState()
    }
}
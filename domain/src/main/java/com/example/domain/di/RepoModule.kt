package com.example.domain.di

import com.example.datasource.api.ApiHelper
import com.example.datasource.cache.dao.CityDAO
import com.example.datasource.cache.dao.ConditionsDAO
import com.example.datasource.prefernce.AppPreference
import com.example.domain.repo.appPreference.AppPreferenceDomain
import com.example.domain.repo.appPreference.AppPreferenceRepo
import com.example.domain.repo.city.CityDomain
import com.example.domain.repo.city.CityDomainRepo
import com.example.domain.repo.conditions.ConditionDomain
import com.example.domain.repo.conditions.ConditionDomainRepo
import com.example.domain.repo.weatherData.WeatherDataDomain
import com.example.domain.repo.weatherData.WeatherDataDomainRepo
import com.example.domain.useCase.citySearch.CitySearch
import com.example.domain.useCase.citySearch.CitySearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RepoModule {


    @Provides
    @Singleton
    fun provideAppPreferenceRepo(appPreference: AppPreference): AppPreferenceDomain {
        return AppPreferenceRepo(appPreference)
    }

    @Singleton
    @Provides
    fun provideCitySearchRepo(apiHelper: ApiHelper, cityDAO: CityDAO): CityDomain {
        return CityDomainRepo(apiHelper,cityDAO)
    }

    @Singleton
    @Provides
    fun provideConditionsRepo(conditionsDAO: ConditionsDAO): ConditionDomain {
        return ConditionDomainRepo(conditionsDAO)
    }


    @Singleton
    @Provides
    fun provideWeatherDataRepo(cityDAO: CityDAO): WeatherDataDomain {
        return WeatherDataDomainRepo(cityDAO)
    }


    @Singleton
    @Provides
    fun provideCitySearchUseCase(apiHelper: ApiHelper): CitySearch {
        return CitySearchUseCase(apiHelper)
    }


}
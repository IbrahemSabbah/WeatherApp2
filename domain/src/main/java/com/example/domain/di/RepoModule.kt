package com.example.domain.di

import com.example.datasource.api.ApiHelper
import com.example.datasource.prefernce.AppPreference
import com.example.domain.repo.appPreference.AppPreferenceDomain
import com.example.domain.repo.appPreference.AppPreferenceRepo
import com.example.domain.repo.citySearch.CitySearch
import com.example.domain.repo.citySearch.CitySearchRepo
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

    @Provides
    @Singleton
    fun provideCitySearchRepo(apiHelper: ApiHelper): CitySearch {
        return CitySearchRepo(apiHelper)
    }

}
package com.example.datasource.di

import android.util.Log
import com.example.datasource.BuildConfig
import com.example.datasource.api.ApiFacade
import com.example.datasource.api.ApiHelper
import com.example.datasource.api.ApiHelperImp
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.HttpUrl
import javax.inject.Qualifier


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {


    companion object {
        init {
            Log.d("NetworkModule", "Load Library")
            System.loadLibrary("native-lib")
        }
    }

    external fun apiKeyFromJni(): String?
    external fun apiUrlFromJni(): String?

    @Provides
    fun provideGSON(): Gson = Gson()

    @Provides
    @Singleton
    fun provideOkHttpClient(keySecretInterceptor: KeySecretInterceptor) =
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val okhttp = OkHttpClient.Builder()
            okhttp.addInterceptor(loggingInterceptor)
            okhttp.addInterceptor(keySecretInterceptor)
            okhttp.build()
        } else OkHttpClient
            .Builder()
            .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, @API_URL BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()


    @Provides
    @Singleton
    fun provideApiFacade(retrofit: Retrofit): ApiFacade = retrofit.create(ApiFacade::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiFacade: ApiFacade): ApiHelper = ApiHelperImp(apiFacade)

    //
    @Singleton
    class KeySecretInterceptor @Inject constructor(@API_KEY private val apiKey: String) :
        Interceptor {

        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var request: Request = chain.request()
            val url: HttpUrl = request.url.newBuilder().addQueryParameter("key", apiKey)
                .addQueryParameter("format", "json").build()
            request = request.newBuilder().url(url).build()
            return chain.proceed(request)
        }
    }


    @Provides
    @Singleton
    @NetworkModule.API_KEY
    fun provideApiKey(): String {
        return apiKeyFromJni()!!

    }


    @Provides
    @Singleton
    @NetworkModule.API_URL
    fun provideApiUrl(): String {
        return apiKeyFromJni()!!

    }

    @Qualifier
    annotation class API_URL
    @Qualifier
    annotation class API_KEY

}
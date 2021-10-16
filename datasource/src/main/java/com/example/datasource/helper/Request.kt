package com.example.datasource.helper

import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception


fun <T> Response<T>.reportError(): HttpException {
    val errorCode = code()
    val errorDescription = errorBody()?.string()

    return HttpException(this)
}
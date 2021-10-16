package com.example.datasource.api.model

sealed class DataState<out R> {
    data class Success <out T>(val data:T): DataState<T>()
    data class Error <out T> (val error:Exception): DataState<T>()
    object Loading: DataState<Nothing>()
}
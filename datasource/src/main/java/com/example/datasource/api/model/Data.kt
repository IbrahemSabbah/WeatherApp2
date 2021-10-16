package com.example.datasource.api.model

data class Data(
    val current_condition: List<CurrentConditionApiEntity>,
    val request: List<RequestApiEntity>
)
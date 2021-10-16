package com.example.domain.mappers

import com.example.datasource.api.model.CitySearchApiEntity
import com.example.datasource.api.model.CurrentConditionApiEntity
import com.example.datasource.api.model.Result

import com.example.datasource.cache.entities.ConditionsEntityCache
import com.example.domain.model.CityNameDomainEntity
import com.example.domain.model.EntityMapper

class CitySearchToCityDomain() :
    EntityMapper<Result, CityNameDomainEntity> {
    override fun mapFromEntity(entity: Result): CityNameDomainEntity {
        return CityNameDomainEntity(
            cityName ="${entity.areaName.first().value}, ${entity.country.first().value}"
        )
    }

    override fun mapToEntity(domainModel: CityNameDomainEntity): Result {
        TODO("Not yet implemented")
    }


}
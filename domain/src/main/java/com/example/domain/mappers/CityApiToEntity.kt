package com.example.domain.mappers

import com.example.datasource.api.model.RequestApiEntity
import com.example.datasource.cache.entities.CityEntityCache
import com.example.domain.model.EntityMapper

class CityApiToEntity : EntityMapper<RequestApiEntity, CityEntityCache> {
    override fun mapFromEntity(entity: RequestApiEntity): CityEntityCache {
        return CityEntityCache(
            name = entity.query,
            color = ""
        )
    }

    override fun mapToEntity(domainModel: CityEntityCache): RequestApiEntity {
        TODO("Not yet implemented")
    }
}
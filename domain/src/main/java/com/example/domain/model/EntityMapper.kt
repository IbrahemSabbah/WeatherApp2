package com.example.domain.model

interface EntityMapper<Entity, DomainModel> {

    fun mapFromEntity(
        entity: Entity
    ): DomainModel

    fun mapToEntity(domainModel: DomainModel): Entity
}
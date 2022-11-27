package com.riztech.githubapp.data.model.mapper

interface ApiMapper<E,D> {
    fun mapToDomain(dataModel: E) : D
}
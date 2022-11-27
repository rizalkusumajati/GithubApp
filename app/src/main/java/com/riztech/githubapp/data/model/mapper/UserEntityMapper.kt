package com.riztech.githubapp.data.model.mapper

import com.riztech.githubapp.data.source.local.GamesEntity
import com.riztech.githubapp.domain.model.Games.Games
import javax.inject.Inject

class UserEntityMapper @Inject constructor(): ApiMapper<Games, GamesEntity> {
    override fun mapToDomain(dataModel: Games): GamesEntity {
        return GamesEntity(
            id = dataModel.id ?: 0,
            added = dataModel.added ?: 0,
            rating = dataModel.rating ?: 0.0,
            name = dataModel.name ?: "",
            genres = dataModel.genres ?: "",
            backgroundImage = dataModel.backgroundImage ?: "",
            description = dataModel.description ?: ""
        )
    }
}
package com.riztech.githubapp.data.model.mapper

import com.riztech.githubapp.data.source.local.GamesEntity
import com.riztech.githubapp.domain.model.Games.Games
import javax.inject.Inject

class EntityUserMapper @Inject constructor(): ApiMapper<GamesEntity, Games> {
    override fun mapToDomain(dataModel: GamesEntity): Games {

            return Games(
                id = dataModel.id,
                added = dataModel.added,
                rating = dataModel.rating,
                name = dataModel.name,
                genres = dataModel.genres,
                backgroundImage = dataModel.backgroundImage,
                description = dataModel.description
            )

    }
}
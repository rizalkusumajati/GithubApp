package com.riztech.githubapp.data.model.mapper

import com.riztech.githubapp.data.model.games.Result
import com.riztech.githubapp.data.source.local.GamesEntity
import javax.inject.Inject

class RemoteLocalUserMapper  @Inject constructor(): ApiMapper<Result, GamesEntity> {
    override fun mapToDomain(dataModel: Result): GamesEntity {
        return GamesEntity(
            id = dataModel.id,
            added = dataModel.added,
            rating = dataModel.rating,
            name = dataModel.name,
            genres = dataModel.genres.joinToString(separator = ",",transform = { genre ->
                genre.name
            }),
            backgroundImage = dataModel.background_image,
            description = dataModel.description
        )
    }
}
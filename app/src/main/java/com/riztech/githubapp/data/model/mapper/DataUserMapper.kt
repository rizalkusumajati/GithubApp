package com.riztech.githubapp.data.model.mapper

import com.riztech.githubapp.data.model.games.Result
import com.riztech.githubapp.domain.model.Games.Games
import javax.inject.Inject

class DataUserMapper @Inject constructor(): ApiMapper<Result, Games> {
    override fun mapToDomain(dataModel: Result): Games {
       return Games(
           id = dataModel.id,
           added = dataModel.added,
           rating = dataModel.rating,
           name = dataModel.name,
           genres = dataModel.genres.joinToString(separator = ",", transform = { genre ->
               genre.name
           }),
           backgroundImage = dataModel.background_image,
           description = dataModel.description
       )
    }
}
package com.riztech.githubapp.domain.repository

import androidx.paging.PagingData
import com.riztech.githubapp.data.model.games.Result
import com.riztech.githubapp.data.source.local.GamesEntity
import com.riztech.githubapp.domain.model.Games.Games
import kotlinx.coroutines.flow.Flow

interface UserRepository {

     suspend fun getUserDetail(id: Int): Result

     fun getSearchUser(query: String):  Flow<PagingData<Result>>

     suspend fun saveFavoriteUser(user: Games)

     suspend fun deleteFavorite(user: Games)

     suspend fun getFavoriteGame(user: Games) : GamesEntity?
}
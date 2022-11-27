package com.riztech.githubapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.riztech.githubapp.data.model.games.Result
import com.riztech.githubapp.data.model.mapper.RemoteLocalUserMapper
import com.riztech.githubapp.data.model.mapper.UserEntityMapper
import com.riztech.githubapp.data.model.paging.GithubPagingSource
import com.riztech.githubapp.data.source.local.GamesEntity
import com.riztech.githubapp.data.source.local.GithubDatabase
import com.riztech.githubapp.data.source.remote.GamesApi
import com.riztech.githubapp.domain.model.Games.Games
import com.riztech.githubapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    val gamesApi: GamesApi,
    val userDatabase: GithubDatabase,
    val mapper: RemoteLocalUserMapper,
    val localMapper: UserEntityMapper
): UserRepository {

    override suspend fun getUserDetail(id: Int): Result {
        return gamesApi.getUserDetail(id)
    }

    override suspend fun saveFavoriteUser(user: Games) {
        userDatabase.gamesDao().insertAll(localMapper.mapToDomain(user))
    }

    override suspend fun deleteFavorite(user: Games) {
        userDatabase.gamesDao().delete(localMapper.mapToDomain(user))
    }

    override suspend fun getFavoriteGame(user: Games): GamesEntity? {
        return userDatabase.gamesDao().getById(user.id ?: 0)
    }

    override  fun getSearchUser(query: String):  Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {GithubPagingSource(gamesApi, query = query)}
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}
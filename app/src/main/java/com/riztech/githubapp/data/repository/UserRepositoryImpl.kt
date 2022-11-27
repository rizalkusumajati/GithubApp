package com.riztech.githubapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.riztech.githubapp.data.model.UserResponse
import com.riztech.githubapp.data.model.UserResponseItem
import com.riztech.githubapp.data.model.mapper.RemoteLocalUserMapper
import com.riztech.githubapp.data.model.mapper.UserEntityMapper
import com.riztech.githubapp.data.model.paging.GithubPagingSource
import com.riztech.githubapp.data.model.paging.GithubRemoteMediator
import com.riztech.githubapp.data.model.paging.NETWORK_PAGE_SIZE
import com.riztech.githubapp.data.source.local.GithubDatabase
import com.riztech.githubapp.data.source.remote.GithubApi
import com.riztech.githubapp.domain.model.User
import com.riztech.githubapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.math.log

class UserRepositoryImpl @Inject constructor(
    val githubApi: GithubApi,
    val userDatabase: GithubDatabase,
    val mapper: RemoteLocalUserMapper,
    val localMapper: UserEntityMapper
): UserRepository {
    override  fun getUser(query: String):  Flow<PagingData<UserResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {GithubPagingSource(githubApi, query = query)}
        ).flow
    }

    override suspend fun getUserDetail(login: String): UserResponseItem {
        return githubApi.getUserDetail(login)
    }

    override suspend fun saveFavoriteUser(user: User) {
        userDatabase.userDao().insertAll(localMapper.mapToDomain(user))
    }

    override  fun getSearchUser(query: String):  Flow<PagingData<UserResponseItem>> {
        // appending '%' so we can allow other characters to be before and after the query string
//        val dbQuery = "%${query.replace(' ', '%')}%"
//        val pagingSourceFactory =  { userDatabase.userDao().reposByName(dbQuery)}

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
//            remoteMediator = {GithubRemoteMediator(query, githubApi, userDatabase, mapper)},
//            pagingSourceFactory = pagingSourceFactory
            pagingSourceFactory = {GithubPagingSource(githubApi, query = query)}
        ).flow
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}
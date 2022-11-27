package com.riztech.githubapp.data.model.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.riztech.githubapp.data.model.mapper.RemoteLocalUserMapper
import com.riztech.githubapp.data.source.local.GithubDatabase
import com.riztech.githubapp.data.source.local.RemoteKeyEntity
import com.riztech.githubapp.data.source.remote.GamesApi
import com.riztech.githubapp.domain.model.Games.Games
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val GITHUB_STARTING_PAGE_INDEX = 1
private const val IN_QUALIFIER = "in:login"

@OptIn(ExperimentalPagingApi::class)
class GithubRemoteMediator @Inject constructor(
    private val query: String,
    private val gamesApi: GamesApi,
    private val userDatabase: GithubDatabase,
    private val mapper: RemoteLocalUserMapper
) : RemoteMediator<Int, Games>() {

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Games>): RemoteKeyEntity? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                userDatabase.remoteKeyDao().remoteKeysRepoId(repo.id?.toLong() ?: 0)
            }
    }
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Games>): RemoteKeyEntity? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                userDatabase.remoteKeyDao().remoteKeysRepoId(repo.id?.toLong() ?: 0)
            }
    }
    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Games>
    ): RemoteKeyEntity? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                userDatabase.remoteKeyDao().remoteKeysRepoId(repoId.toLong())
            }
        }
    }
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Games>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: GITHUB_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with endOfPaginationReached = false because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }
        val apiQuery = query + IN_QUALIFIER

        try {
            val apiResponse = gamesApi.getSearchUser(apiQuery, page, state.config.pageSize)

            val repos = apiResponse.results
            val endOfPaginationReached = repos.isEmpty()
            userDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    userDatabase.remoteKeyDao().clearRemoteKeys()
                    userDatabase.gamesDao().clearUser()
                }
                val prevKey = if (page == GITHUB_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = repos.map {
                    RemoteKeyEntity(repoId = it.id.toLong(), prevKey = prevKey, nextKey = nextKey)
                }
                val entity = repos.map {
                    mapper.mapToDomain(it)
                }
                userDatabase.remoteKeyDao().insertAll(keys)
                userDatabase.gamesDao().insertAll(entity)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
}
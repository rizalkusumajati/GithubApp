package com.riztech.githubapp.data.model.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.riztech.githubapp.data.model.games.Result
import com.riztech.githubapp.data.source.remote.GamesApi
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1
const val NETWORK_PAGE_SIZE = 30

class GithubPagingSource(
    private val gamesApi: GamesApi,
    private val query: String
): PagingSource<Int, Result>() {

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        val apiQuery = query

        return try {
            val response = gamesApi.getSearchUser(apiQuery, position, params.loadSize)
            val users = response.results
            val nextKey = if(users.isEmpty()){
                null
            }else{
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = users,
                prevKey = if(position == GITHUB_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        }catch (exception: IOException){
            return LoadResult.Error(exception)
        }catch (exception: HttpException){
            return LoadResult.Error(exception)
        }
    }
}
package com.riztech.githubapp.data.model.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.riztech.githubapp.data.model.UserResponse
import com.riztech.githubapp.data.model.UserResponseItem
import com.riztech.githubapp.data.source.remote.GithubApi
import retrofit2.HttpException
import java.io.IOException

private const val GITHUB_STARTING_PAGE_INDEX = 1
private const val IN_QUALIFIER = "in:login"
const val NETWORK_PAGE_SIZE = 30

class GithubPagingSource(
    private val githubApi: GithubApi,
    private val query: String
): PagingSource<Int, UserResponseItem>() {


    override fun getRefreshKey(state: PagingState<Int, UserResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)

        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserResponseItem> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        val apiQuery = query + IN_QUALIFIER

        return try {
            val response = githubApi.getSearchUser(apiQuery, position, params.loadSize)
            val users = response.items
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
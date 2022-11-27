package com.riztech.githubapp.data.source.remote

import com.riztech.githubapp.data.model.games.GamesResponse
import com.riztech.githubapp.data.model.games.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GamesApi {
    @GET("games/{id}")
    suspend fun getUserDetail(
        @Path("id") id: Int,
        @Query("key") key: String = "c3e6372f19b0456ea0c15fea1533e42c"
    ): Result

    @GET("games")
    suspend fun getSearchUser(
        @Query("search") query: String,
        @Query("page") page: Int,
        @Query("page_size") itemsPerPage: Int,
        @Query("key") key: String = "c3e6372f19b0456ea0c15fea1533e42c"
    ): GamesResponse
}
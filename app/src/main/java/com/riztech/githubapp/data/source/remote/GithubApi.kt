package com.riztech.githubapp.data.source.remote

import com.riztech.githubapp.data.model.UserResponse
import com.riztech.githubapp.data.model.UserResponseItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @GET("/users")
    suspend fun getUser(): UserResponse

    @GET("/users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): UserResponseItem

    @GET("/search/users")
    suspend fun getSearchUser(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): UserResponse
}
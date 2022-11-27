package com.riztech.githubapp.domain.repository

import androidx.paging.PagingData
import com.riztech.githubapp.data.model.UserResponse
import com.riztech.githubapp.data.model.UserResponseItem
import com.riztech.githubapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

     fun getUser(query: String):  Flow<PagingData<UserResponseItem>>

     suspend fun getUserDetail(login: String): UserResponseItem

     fun getSearchUser(query: String):  Flow<PagingData<UserResponseItem>>

     suspend fun saveFavoriteUser(user: User)
}
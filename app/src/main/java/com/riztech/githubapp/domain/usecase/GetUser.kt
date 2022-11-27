package com.riztech.githubapp.domain.usecase

import com.riztech.githubapp.data.model.mapper.DataUserMapper
import com.riztech.githubapp.data.source.remote.GithubApi
import com.riztech.githubapp.domain.model.User
import com.riztech.githubapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUser @Inject constructor(
    val userRepository: UserRepository,
    val mapper: DataUserMapper
) {

//    suspend fun invoke(query: String): List<User> {
//        return userRepository.getSearchUser().map {
//                mapper.mapToDomain(it)
//            }
//
//    }
}
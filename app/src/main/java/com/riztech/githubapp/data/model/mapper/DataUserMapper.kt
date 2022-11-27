package com.riztech.githubapp.data.model.mapper

import com.riztech.githubapp.data.model.UserResponseItem
import com.riztech.githubapp.domain.model.User
import javax.inject.Inject

class DataUserMapper @Inject constructor(): ApiMapper<UserResponseItem, User> {
    override fun mapToDomain(dataModel: UserResponseItem): User {
       return User(
           id = dataModel.id,
           avatar_url = dataModel.avatar_url,
           login = dataModel.login,
           organizations_url = dataModel.organizations_url,
           received_events_url = dataModel.received_events_url,
           repos_url = dataModel.repos_url,
           site_admin = dataModel.site_admin,
           starred_url = dataModel.starred_url,
           subscriptions_url = dataModel.subscriptions_url,
           type = dataModel.type,
           url = dataModel.url
       )
    }
}
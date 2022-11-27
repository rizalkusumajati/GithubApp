package com.riztech.githubapp.data.model.mapper

import com.riztech.githubapp.data.source.local.UserEntity
import com.riztech.githubapp.domain.model.User
import javax.inject.Inject

class UserEntityMapper @Inject constructor(): ApiMapper<User, UserEntity> {
    override fun mapToDomain(dataModel: User): UserEntity {
        return UserEntity(
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
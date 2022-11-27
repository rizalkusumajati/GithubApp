package com.riztech.githubapp.domain.model

data class User(
    val id: Int,
    val avatar_url: String,
    val login: String,
    val organizations_url: String,
    val received_events_url: String,
    val repos_url: String,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val type: String,
    val url: String
)

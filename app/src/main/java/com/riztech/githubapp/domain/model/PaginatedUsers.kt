package com.riztech.githubapp.domain.model

data class PaginatedUsers (
    val users: List<User>,
    val pagination: Pagination
    )
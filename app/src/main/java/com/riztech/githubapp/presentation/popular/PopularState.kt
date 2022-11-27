package com.riztech.githubapp.presentation.popular

const val DEFAULT_QUERY = ""
data class PopularState (
    val query: String = DEFAULT_QUERY,
    val lastQueryScrolled: String = DEFAULT_QUERY,
    val hasNotScrolledForCurrentSearch: Boolean = false
)
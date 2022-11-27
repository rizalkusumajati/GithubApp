package com.riztech.githubapp.domain.model.Games

import com.riztech.githubapp.data.model.games.Genre

data class Games(
    val id: Int? = 0,
    val added: Int? = 0,
    val rating: Double? = 0.0,
    val name: String? = "",
    val genres: String? = "",
    val backgroundImage: String? = "",
    val description: String? = "",
    val isFavorite: Boolean? = false
)

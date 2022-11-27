package com.riztech.githubapp.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GamesEntity (
    @PrimaryKey
    val id: Int,
    val added: Int,
    val rating: Double,
    val name: String,
    val genres: String,
    val backgroundImage: String,
    val description: String
)
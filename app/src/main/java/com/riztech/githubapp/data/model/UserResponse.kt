package com.riztech.githubapp.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse (
    @SerializedName("total_count") val total: Int = 0,
    @SerializedName("items") val items: List<UserResponseItem> = emptyList(),
    @SerializedName("incomplete_results") val incompleteResults: Boolean = false
)
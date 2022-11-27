package com.riztech.githubapp.presentation.util

sealed class Result<out T> {
    data class Success<out R>(val value: R): Result<R>()
    data class Failure(
        val message: String?,
        val throwable: Throwable?
    ): Result<Nothing>()
    object Loading : Result<Nothing>()
}
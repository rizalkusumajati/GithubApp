package com.riztech.githubapp.presentation.util

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class UserLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<UserLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: UserLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): UserLoadStateViewHolder {
        return UserLoadStateViewHolder.create(parent, retry)
    }
}
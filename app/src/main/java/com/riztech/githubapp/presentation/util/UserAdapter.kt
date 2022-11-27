package com.riztech.githubapp.presentation.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riztech.githubapp.databinding.UserListItemBinding
import com.riztech.githubapp.domain.model.Games.Games

class UserAdapter(val clickDetail: (user: Games?) -> Unit) : PagingDataAdapter<Games, UserAdapter.UserViewHolder>(REPO_COMPARATOR) {
    inner class UserViewHolder( val binding: UserListItemBinding): RecyclerView.ViewHolder(binding.root){

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
       with(holder){
           val userItem = getItem(position)
           with(userItem){
               this?.let {
                   binding.tvLogin.text = name
                   binding.ivRating.text = rating.toString()
                   binding.tvAdded.text = added.toString()
                   binding.root.setOnClickListener {
                       clickDetail.invoke(userItem)
                   }
                   binding.tvDesc.text = genres
                   Glide.with(binding.root).load(backgroundImage).into(binding.ivAvatar)
               }

           }
       }
    }


    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Games>() {
            override fun areItemsTheSame(oldItem: Games, newItem: Games): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Games, newItem: Games): Boolean =
                oldItem == newItem
        }
    }
}
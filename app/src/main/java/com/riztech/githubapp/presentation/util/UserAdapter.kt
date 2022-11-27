package com.riztech.githubapp.presentation.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riztech.githubapp.data.model.UserResponseItem
import com.riztech.githubapp.databinding.UserListItemBinding
import com.riztech.githubapp.domain.model.User

class UserAdapter(val clickDetail: (user: User?) -> Unit) : PagingDataAdapter<User, UserAdapter.UserViewHolder>(REPO_COMPARATOR) {
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
                   binding.tvLogin.text = login
                   binding.ivUrl.text = repos_url
                   binding.root.setOnClickListener {
                       clickDetail.invoke(userItem)
                   }
                   Glide.with(binding.root).load(avatar_url).into(binding.ivAvatar)
               }

           }
       }
    }


    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.login == newItem.login

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
    }
}
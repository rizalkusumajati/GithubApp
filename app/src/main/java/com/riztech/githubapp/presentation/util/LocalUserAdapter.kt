package com.riztech.githubapp.presentation.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riztech.githubapp.databinding.UserListItemBinding
import com.riztech.githubapp.domain.model.User

class LocalUserAdapter (val users: List<User>, val clickDetail: (user: User?) -> Unit) : RecyclerView.Adapter<LocalUserAdapter.LocalUserViewHolder>() {

    inner class LocalUserViewHolder(val binding: UserListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(user: User){
                binding.tvLogin.text = user.login
                binding.ivUrl.text = user.repos_url
                binding.root.setOnClickListener {
                    clickDetail.invoke(user)
                }
                Glide.with(binding.root).load(user.avatar_url).into(binding.ivAvatar)

            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalUserViewHolder {
        val binding =
            UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocalUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocalUserViewHolder, position: Int) {
        holder.bind(user = users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }
}
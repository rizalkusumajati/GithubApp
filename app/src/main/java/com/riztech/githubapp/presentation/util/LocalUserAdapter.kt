package com.riztech.githubapp.presentation.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riztech.githubapp.databinding.UserListItemBinding
import com.riztech.githubapp.domain.model.Games.Games

class LocalUserAdapter (val users: List<Games>, val clickDetail: (user: Games?) -> Unit) : RecyclerView.Adapter<LocalUserAdapter.LocalUserViewHolder>() {

    inner class LocalUserViewHolder(val binding: UserListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(game: Games){
                binding.tvLogin.text = game.name
                binding.ivRating.text = game.rating.toString()
                binding.tvAdded.text = game.added.toString()
                binding.root.setOnClickListener {
                    clickDetail.invoke(game)
                }
                binding.tvDesc.text = game.genres
                Glide.with(binding.root).load(game.backgroundImage).into(binding.ivAvatar)

            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalUserViewHolder {
        val binding =
            UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocalUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocalUserViewHolder, position: Int) {
        holder.bind(game = users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }
}
package io.github.codejunk1e.githubx.users

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.codejunk1e.githubx.databinding.RepositoriesListItemBinding
import io.github.codejunk1e.githubx.databinding.UsersListItemBinding
import io.github.codejunk1e.githubx.ext.loadImage
import io.github.codejunk1e.githubx.ext.show
import io.github.codejunk1e.githubx.domain.Repository
import io.github.codejunk1e.githubx.domain.User

class UsersAdapter(val onCLick: (String) -> Unit): ListAdapter<User, UsersAdapter.ViewHolder>(diffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UsersListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(private val binding: UsersListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User){
            binding.apply {
                avatar.loadImage(item.image)
                name.text = item.name
                root.setOnClickListener { onCLick(item.login) }
            }
        }
    }
}

val diffCallback = object : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
}
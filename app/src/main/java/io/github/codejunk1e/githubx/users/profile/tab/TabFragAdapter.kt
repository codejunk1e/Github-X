package io.github.codejunk1e.githubx.users.profile.tab

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.codejunk1e.githubx.databinding.RepositoriesListItemBinding
import io.github.codejunk1e.githubx.databinding.TabRepositoriesListItemBinding
import io.github.codejunk1e.githubx.domain.UserRepo
import io.github.codejunk1e.githubx.ext.getTimeDifference
import io.github.codejunk1e.githubx.ext.hide
import io.github.codejunk1e.githubx.ext.show

class TabFragAdapter: ListAdapter<UserRepo, TabFragAdapter.ViewHolder>(diffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TabRepositoriesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(private val binding: TabRepositoriesListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserRepo){
            binding.apply {
                userName.text = item.userName
                repoName.text = item.repoName
                if (item.isPublic) publicBadge.show() else publicBadge.hide()
                starGazers.text = item.starGazers.toString()
                language.text = item.language
                languageColor.setBackgroundColor(Color.parseColor(item.languageColor))
                item.description?.let { description.text = it }
                updatedAt.text = "Updated ${getTimeDifference(item.lastUpdatedDateString)}"
            }
        }
    }
}

val diffCallback = object : DiffUtil.ItemCallback<UserRepo>() {
    override fun areItemsTheSame(oldItem: UserRepo, newItem: UserRepo) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: UserRepo, newItem: UserRepo) = oldItem == newItem
}
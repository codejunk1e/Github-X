package io.github.codejunk1e.githubx.repositories

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import io.github.codejunk1e.githubx.databinding.RepositoriesListItemBinding
import io.github.codejunk1e.githubx.ext.loadImage
import io.github.codejunk1e.githubx.ext.show
import io.github.codejunk1e.githubx.domain.Repository
import io.github.codejunk1e.githubx.ext.hide

class RepositoriesAdapter: ListAdapter<Repository, RepositoriesAdapter.ViewHolder>(diffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RepositoriesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(private val binding: RepositoriesListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Repository){
            binding.apply {
                item.avatar?.let { avatar.loadImage(it) }
                starGazers.text = item.stars.toString()
                binding.userName.text = item.developerName
                binding.repoName.text = item.projectName
                languageColor.setBackgroundColor(Color.parseColor(item.languageColor))
                language.text = item.language ?: "N/A"
                description.text = item.description

                item.topics?.forEachIndexed { index, topic ->
                    when(index){
                        0 -> {
                            firstTopic.show()
                            firstTopic.text = topic
                        }
                        1 -> {
                            secondTopic.show()
                            secondTopic.text = topic
                        }
                        2 -> {
                            thirdTopic.show()
                            thirdTopic.text = topic
                        }
                    }
                }

                if (item.description.isNullOrEmpty()) description.hide() else description.show()
                if (item.topics?.isEmpty() == true) topicsContainer.hide() else topicsContainer.show()
            }
        }
    }
}

val diffCallback = object : DiffUtil.ItemCallback<Repository>() {
    override fun areItemsTheSame(oldItem: Repository, newItem: Repository) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Repository, newItem: Repository) = oldItem == newItem
}
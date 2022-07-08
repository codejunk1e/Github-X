package io.github.codejunk1e.githubx.datasource.mappers

import io.github.codejunk1e.githubx.datasource.remote.models.RepositoryDTO
import io.github.codejunk1e.githubx.domain.Repository

fun List<RepositoryDTO>.toDomainModel() :List<Repository>{
    return this.map {
        val name: List<String> = it.full_name.split("/")
        Repository(
            it.id,
            it.owner.avatar_url,
            name[0],
            name[1],
            it.stargazers_count,
            it.language,
            "#00FF00",
            it.description,
            it.topics
        )
    }
}
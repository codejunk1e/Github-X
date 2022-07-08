package io.github.codejunk1e.githubx.datasource.mappers

import io.github.codejunk1e.githubx.datasource.remote.models.UserRepoDTO
import io.github.codejunk1e.githubx.domain.UserRepo

fun List<UserRepoDTO>.toDomainModel(): List<UserRepo> {
    return this.map {
        val names = it.full_name.split("/")
        UserRepo(
            it.id,
            names[0],
            names[1],
            !it.private,
            it.stargazers_count,
            it.language,
            "#000000",
            it.description,
            null,
            it.updated_at
        )
    }
}
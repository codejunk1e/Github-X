package io.github.codejunk1e.githubx.datasource.mappers

import io.github.codejunk1e.githubx.datasource.remote.models.UserDTO
import io.github.codejunk1e.githubx.domain.User

fun List<UserDTO>.toDomainModel() :List<User>{
    return this.map {
        User(
            it.id,
            it.login,
            it.avatar_url,
            it.login,
            it.login,
            null,
            null,
            null
        )
    }
}
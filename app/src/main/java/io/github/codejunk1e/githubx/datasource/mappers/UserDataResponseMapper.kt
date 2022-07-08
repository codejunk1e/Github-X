package io.github.codejunk1e.githubx.datasource.mappers

import io.github.codejunk1e.githubx.datasource.remote.models.UserDTO
import io.github.codejunk1e.githubx.datasource.remote.models.UserDetailsDTO
import io.github.codejunk1e.githubx.domain.User
import io.github.codejunk1e.githubx.domain.UserData

fun UserDetailsDTO.toDomainModel() :UserData{
    return UserData(
        this.id,
        this.avatar_url,
        this.name,
        this.login,
        this.bio,
        this.location,
        this.html_url,
        this.followers,
        this.following,
        this.public_repos,
        this.login
    )
}
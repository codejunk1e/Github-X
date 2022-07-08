package io.github.codejunk1e.githubx.datasource.remote.models

data class UserDetailsDTO(
    val avatar_url: String?,
    val bio: String?,
    val email: String?,
    val html_url: String,
    val followers: Int,
    val following: Int,
    val id: Int,
    val location: String?,
    val login: String,
    val name: String,
    val public_repos: Int,
)
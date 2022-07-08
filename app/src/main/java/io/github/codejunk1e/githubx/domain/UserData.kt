package io.github.codejunk1e.githubx.domain

data class UserData(
    val id: Int,
    val image: String?,
    val name: String,
    val userName: String?,
    val bio: String?,
    val location: String?,
    val link: String?,
    val followers: Int,
    val following: Int,
    val repos: Int,
    val login: String,
)

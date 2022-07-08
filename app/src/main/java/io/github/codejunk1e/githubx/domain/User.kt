package io.github.codejunk1e.githubx.domain

data class User(
    val id: Int,
    val login: String,
    val image: String,
    val name: String,
    val userName: String?,
    val bio: String?,
    val location: String?,
    val email: String?
)

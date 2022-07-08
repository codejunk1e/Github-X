package io.github.codejunk1e.githubx.domain

data class UserRepo(
    val id: Int,
    val userName: String,
    val repoName: String,
    val isPublic: Boolean,
    val starGazers: Int,
    val language: String?,
    val languageColor: String?,
    val description: String?,
    val forkedLocation: String?,
    val lastUpdatedDateString: String
)

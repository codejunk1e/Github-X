package io.github.codejunk1e.githubx.datasource.remote.models

data class RepositoryDTO(
    val id: Int,
    val full_name: String,
    val owner: Owner,
    val language: String?,
    val description: String?,
    val topics: List<String>?,
    val stargazers_count: Int,
    val languages_url: String?,
)

data class Owner(val avatar_url: String?)
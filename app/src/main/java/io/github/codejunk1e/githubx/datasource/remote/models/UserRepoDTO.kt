package io.github.codejunk1e.githubx.datasource.remote.models

data class UserRepoDTO(
    val created_at: String,
    val description: String?,
    val full_name: String,
    val id: Int,
    val language: String?,
    val name: String,
    val node_id: String,
    val owner: Owner,
    val `private`: Boolean,
    val stargazers_count: Int,
    val topics: List<String>,
    val updated_at: String,
    val visibility: String,
)
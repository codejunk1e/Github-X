package io.github.codejunk1e.githubx.domain

data class Repository(
    val id: Int,
    val avatar: String?,
    val developerName: String,
    val projectName: String,
    val stars: Int,
    val language: String?,
    val languageColor: String?,
    val description: String?,
    val topics: List<String>?
)

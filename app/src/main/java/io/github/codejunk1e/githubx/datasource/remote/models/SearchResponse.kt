package io.github.codejunk1e.githubx.datasource.remote.models

data class SearchResponse<T>(
    val incomplete_results: Boolean,
    val total_count: Int,
    val items: List<T>
)
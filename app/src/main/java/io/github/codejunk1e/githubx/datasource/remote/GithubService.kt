package io.github.codejunk1e.githubx.datasource.remote

import io.github.codejunk1e.githubx.datasource.remote.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService{

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("per_page") items: Int = 50
    ): SearchResponse<RepositoryDTO>

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String,
        @Query("per_page") items: Int = 100
    ): SearchResponse<UserDTO>

    @GET("users/{id}")
    suspend fun getUserDetails(
        @Path("id") path: String,
    ): UserDetailsDTO

    @GET("users/{user}/repos")
    suspend fun getUsersRepos(
        @Path("user") user: String,
    ): List<UserRepoDTO>
}

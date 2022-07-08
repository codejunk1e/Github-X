package io.github.codejunk1e.githubx.datasource

import io.github.codejunk1e.githubx.datasource.mappers.toDomainModel
import io.github.codejunk1e.githubx.datasource.remote.GithubService
import io.github.codejunk1e.githubx.domain.Repository
import io.github.codejunk1e.githubx.domain.User
import io.github.codejunk1e.githubx.domain.UserData
import io.github.codejunk1e.githubx.domain.UserRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GitHubRepository @Inject constructor(
    val dispatcher: CoroutineDispatcher,
    val service: GithubService
) {
    suspend fun searchRepo(text: String): List<Repository> = withContext(dispatcher) {
        val response = service.searchRepositories(text)
        response.items.toDomainModel()
    }

    suspend fun searchUser(text: String): List<User>  = withContext(dispatcher) {
        val response = service.searchUser(text)
        response.items.toDomainModel()
    }

    suspend fun getUser(id: String): UserData  = withContext(dispatcher) {
        val response = service.getUserDetails(id)
        response.toDomainModel()
    }

    suspend fun getUsersRepos(user: String): List<UserRepo>  = withContext(dispatcher) {
        val response = service.getUsersRepos(user)
        response.toDomainModel()
    }

}
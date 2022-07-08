package io.github.codejunk1e.githubx.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.codejunk1e.githubx.datasource.GitHubRepository
import io.github.codejunk1e.githubx.domain.Repository
import io.github.codejunk1e.githubx.domain.User
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class UsersViewmodel @Inject constructor(): ViewModel() {

    @Inject lateinit var repository: GitHubRepository
    private val _state = MutableStateFlow(UsersFragState())
    val state: StateFlow<UsersFragState> = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _state.update { current ->
            if (exception is UnknownHostException){
                current.copy(isLoading = false, error = "Failed to fetch data, Please check internet!")
            } else current.copy(isLoading = false, error = exception.message)
        }
    }


    fun searchUser(text: String) {
        if (text.isNotEmpty()){
            _state.update { it.copy(isLoading = true)}
            viewModelScope.launch(exceptionHandler) {
                val result = repository.searchUser(text)
                _state.update { current ->
                    current.copy(isLoading = false, users = result)
                }
            }
        }
    }

    fun errorShown() {
        _state.update { current ->
            current.copy(error = null)
        }
    }
}

data class UsersFragState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
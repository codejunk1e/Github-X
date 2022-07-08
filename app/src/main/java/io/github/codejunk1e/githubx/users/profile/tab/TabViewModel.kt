package io.github.codejunk1e.githubx.users.profile.tab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.codejunk1e.githubx.datasource.GitHubRepository
import io.github.codejunk1e.githubx.domain.UserRepo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class TabViewModel@Inject constructor(): ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _state.update { current ->
            Timber.e(exception)
            if (exception is UnknownHostException){
                current.copy(isLoading = false, error = "Failed to fetch data, Please check internet!")
            } else current.copy(isLoading = false, error = exception.message)
        }
    }

    @Inject
    lateinit var repository: GitHubRepository
    private val _state = MutableStateFlow(TabFragState(isLoading = true))
    val state: StateFlow<TabFragState> = _state


    fun getUsersRepos(user: String) {
        viewModelScope.launch(exceptionHandler) {
            _state.update { it.copy(isLoading = true)}
            val result = repository.getUsersRepos(user)
            _state.update { current ->
                current.copy(isLoading = false, repos = result)
            }
        }
    }

    fun errorShown() {
        _state.update { current ->
            current.copy(error = null)
        }
    }
}

data class TabFragState(
    val repos: List<UserRepo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
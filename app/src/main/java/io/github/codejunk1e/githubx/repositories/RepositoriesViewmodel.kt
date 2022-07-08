package io.github.codejunk1e.githubx.repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.codejunk1e.githubx.datasource.GitHubRepository
import io.github.codejunk1e.githubx.domain.Repository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(): ViewModel() {

    @Inject lateinit var repository: GitHubRepository
    private val _state = MutableStateFlow(RepositoriesState())
    val state: StateFlow<RepositoriesState> = _state

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _state.update { current ->
            if (exception is UnknownHostException){
                current.copy(isLoading = false, error = "Failed to fetch data, Please check internet!")
            } else current.copy(isLoading = false, error = exception.message)
        }
    }


    fun searchRepo(text: String) {
        if (text.isNotEmpty()){
            _state.update { it.copy(isLoading = true)}
            viewModelScope.launch(exceptionHandler) {
                val result = repository.searchRepo(text)
                _state.update { current ->
                    current.copy(isLoading = false, repositories = result)
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

data class RepositoriesState(
    val repositories: List<Repository> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
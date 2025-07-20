package com.jaseem.githubexplorer.ui.state

import com.jaseem.githubexplorer.api.Resource

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class DirtyLoading<T>(val data: T) : UiState<T>
    data class Success<T>(val data: T) : UiState<T>
    data class Error<T>(val throwable: Throwable) : UiState<T>
}

internal suspend inline fun <reified T> uiStateWrapper(
    crossinline apiCall: suspend () -> Resource<T>
): UiState<T>
    = when (val response = apiCall()) {
        is Resource.Error -> UiState.Error(response.throwable)
        is Resource.Failure -> UiState.Error(response.throwable)
        is Resource.Success -> UiState.Success(response.data)
    }


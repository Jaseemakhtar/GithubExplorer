package com.jaseem.githubexplorer.ui.listscreen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaseem.githubexplorer.data.common.UserSearchItemResponse
import com.jaseem.githubexplorer.data.listscreen.ListScreenRepository
import com.jaseem.githubexplorer.domain.usecase.SearchQuerySanitizerUseCase
import com.jaseem.githubexplorer.ui.state.UiState
import com.jaseem.githubexplorer.ui.state.uiStateWrapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListScreenViewModel(
    private val listScreenRepository: ListScreenRepository,
    private val searchQuerySanitizer: SearchQuerySanitizerUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<UserSearchItemResponse>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private var searchQueryDebounceJob: Job? = null

    init {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            _uiState.value = uiStateWrapper {
                listScreenRepository.getAllUsers()
            }

            Log.d("DebooogRESP", "${uiState.value} ")
        }
    }

    private fun searchUsers(sanitizedQuery: String) {
        searchQueryDebounceJob?.cancel()
        searchQueryDebounceJob = viewModelScope.launch {
            delay(700)
            val trimmedText = sanitizedQuery.trim()

            val oldData = (uiState.value as? UiState.Success)?.data

            _uiState.value = UiState.DirtyLoading(oldData ?: emptyList())

            val res =  uiStateWrapper { listScreenRepository.searchUsers(trimmedText) }

            when (res) {
                is UiState.Error -> {
                    _uiState.value = UiState.Error(res.throwable)
                }


                is UiState.Success -> {
                    _uiState.value = UiState.Success(res.data.items)
                }

                else -> {
                    /* no-op */
                }
            }

            Log.d("DebooogRESP", "$res ")
        }
    }

    fun onSearchQueryChange(query: String) {
        val sanitizedQuery = searchQuerySanitizer.invoke(query)
        _searchQuery.value = sanitizedQuery

        searchUsers(sanitizedQuery)
    }
}
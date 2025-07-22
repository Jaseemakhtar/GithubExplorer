package com.jaseem.githubexplorer.ui.listscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jaseem.githubexplorer.data.listscreen.ListScreenRepository
import com.jaseem.githubexplorer.domain.usecase.SearchQuerySanitizerUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

class ListScreenViewModel(
    private val listScreenRepository: ListScreenRepository,
    private val searchQuerySanitizer: SearchQuerySanitizerUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val pagedUsers = searchQuery
        .debounce(700)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            val trimmedQuery = query.trim()

            if (trimmedQuery.isEmpty()) {
                listScreenRepository.getAllUsers().flow
            } else {
                listScreenRepository.searchUsers(query).flow
            }
        }
        .cachedIn(viewModelScope)

    fun onSearchQueryChange(query: String) {
        val sanitizedQuery = searchQuerySanitizer.invoke(query)
        _searchQuery.value = sanitizedQuery
    }
}
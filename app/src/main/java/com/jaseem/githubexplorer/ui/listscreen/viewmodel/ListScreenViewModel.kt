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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

class ListScreenViewModel(
    private val listScreenRepository: ListScreenRepository,
    private val searchQuerySanitizer: SearchQuerySanitizerUseCase
) : ViewModel() {
    private val _selectedLocation = MutableStateFlow(Location.None)
    val selectedLocation = _selectedLocation.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val availableLocations = Location.entries

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val users = combine(
        searchQuery
            .debounce(600)
            .distinctUntilChanged(),
        selectedLocation
    ) { query, location ->
        query.trim() to location
    }.flatMapLatest { queryLocationPair ->
        val query = queryLocationPair.first
        val location = queryLocationPair.second

        if (query.isEmpty() && location == Location.None) {
            listScreenRepository.getAllUsers().flow
        } else {
            listScreenRepository.searchUsers(query, location).flow
        }
    }.cachedIn(viewModelScope)

    fun onSearchQueryChange(query: String) {
        val sanitizedQuery = searchQuerySanitizer.invoke(query)
        _searchQuery.value = sanitizedQuery
    }

    fun onLocationFilterChange(location: Location) {
        _selectedLocation.value = location
    }
}

enum class Location(val countryName: String) {
    None("None"),
    Uae("Dubai"),
    Japan("Japan"),
    India("India")
}
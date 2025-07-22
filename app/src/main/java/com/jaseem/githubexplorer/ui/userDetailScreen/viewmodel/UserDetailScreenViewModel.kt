package com.jaseem.githubexplorer.ui.userDetailScreen.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import com.jaseem.githubexplorer.data.common.model.UserDetailResponse
import com.jaseem.githubexplorer.data.detailscreen.UserDetailRepository
import com.jaseem.githubexplorer.nav.UserDetailRoute
import com.jaseem.githubexplorer.ui.state.UiState
import com.jaseem.githubexplorer.ui.state.uiStateWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserDetailScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val userDetailRepository: UserDetailRepository
) : ViewModel() {
    private val route = savedStateHandle.toRoute<UserDetailRoute>()

    private val _uiState = MutableStateFlow<UiState<UserDetailResponse>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    val repositories =
            userDetailRepository
                .getUnforkedRepositories(route.userName).flow
                .cachedIn(viewModelScope)

    fun init() {
        viewModelScope.launch {
            delay(3000)
            _uiState.value = uiStateWrapper { userDetailRepository.getUserDetails(route.userId) }
        }
    }
}



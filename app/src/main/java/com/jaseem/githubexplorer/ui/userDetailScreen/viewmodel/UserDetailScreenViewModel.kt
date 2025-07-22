package com.jaseem.githubexplorer.ui.userDetailScreen.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jaseem.githubexplorer.api.Resource
import com.jaseem.githubexplorer.data.common.model.UserDetailResponse
import com.jaseem.githubexplorer.data.detailscreen.UserDetailRepository
import com.jaseem.githubexplorer.data.detailscreen.model.RepositoryDetail
import com.jaseem.githubexplorer.nav.UserDetailRoute
import com.jaseem.githubexplorer.ui.state.UiState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserDetailScreenViewModel(
    savedStateHandle: SavedStateHandle,
    private val userDetailRepository: UserDetailRepository
) : ViewModel() {
    private val route = savedStateHandle.toRoute<UserDetailRoute>()

    private val _uiState = MutableStateFlow<UiState<Pair<UserDetailResponse, List<RepositoryDetail>>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {

            val userDetailsAsync = async { userDetailRepository.getUserDetails(route.userId) }
            val userRepositoriesAsync =
                async { userDetailRepository.getUserRepositories(route.userName) }

            val userDetails = userDetailsAsync.await()
            val userRepositories = userRepositoriesAsync.await()

            when (userDetails) {
                is Resource.Error -> {
                    _uiState.value = UiState.Error(userDetails.throwable)
                }

                is Resource.Failure -> {
                    _uiState.value = UiState.Error(userDetails.throwable)
                }

                is Resource.Success -> {
                    val userRepositories = when (userRepositories) {
                        is Resource.Success -> userRepositories.data.filter { it.fork.not() }
                        else -> emptyList()
                    }

                    _uiState.value = UiState.Success(
                        Pair(userDetails.data, userRepositories)
                    )
                }

            }

            Log.d("DebooogRESP", "userDetails: ${uiState.value}")
        }
    }


}
package com.jaseem.githubexplorer.ui.userDetailScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.jaseem.githubexplorer.api.GitHubClient
import com.jaseem.githubexplorer.data.detailscreen.UserDetailRepositoryImp
import kotlin.reflect.KClass

class UserDetailViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        val api = GitHubClient.githubApiService
        val repository = UserDetailRepositoryImp(api)
        val savedStateHandle = extras.createSavedStateHandle()

        return UserDetailScreenViewModel(savedStateHandle, repository) as T
    }
}

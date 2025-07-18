package com.jaseem.githubexplorer.ui.userDetailScreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jaseem.githubexplorer.api.GitHubClient
import com.jaseem.githubexplorer.data.detailscreen.UserDetailRepositoryImp

class UserDetailViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val api = GitHubClient.githubApiService
        val repository = UserDetailRepositoryImp(api)
        return UserDetailScreenViewModel(repository) as T
    }
}

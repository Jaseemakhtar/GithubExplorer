package com.jaseem.githubexplorer.ui.listscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jaseem.githubexplorer.api.GitHubClient
import com.jaseem.githubexplorer.data.listscreen.ListScreenRepositoryImp

class ListScreenViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val api = GitHubClient.githubApiService
        val repository = ListScreenRepositoryImp(api)
        return ListScreenViewModel(repository) as T
    }
}

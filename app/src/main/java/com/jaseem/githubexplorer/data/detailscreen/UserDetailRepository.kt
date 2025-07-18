package com.jaseem.githubexplorer.data.detailscreen

import com.jaseem.githubexplorer.api.GitHubApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface UserDetailRepository {

}

class UserDetailRepositoryImp (
    private val api: GitHubApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): UserDetailRepository {

    suspend fun getUserDetails(username: String) = withContext(dispatcher) {

    }
}
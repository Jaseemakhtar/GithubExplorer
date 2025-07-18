package com.jaseem.githubexplorer.data.listscreen

import com.jaseem.githubexplorer.api.GitHubApiService
import com.jaseem.githubexplorer.api.Resource
import com.jaseem.githubexplorer.api.resourceWrapper
import com.jaseem.githubexplorer.data.common.UserSearchItemResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ListScreenRepository {
    suspend fun getAllUsers(): Resource<List<UserSearchItemResponse>>
}

class ListScreenRepositoryImp(
    private val api: GitHubApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ListScreenRepository {

    override suspend fun getAllUsers(): Resource<List<UserSearchItemResponse>> = withContext(dispatcher) {
        resourceWrapper {
            api.getAllUsers()
        }
    }

    suspend fun getOrgMembers(org: String) = withContext(dispatcher) {

    }

    suspend fun searchUsers(query: String) = withContext(dispatcher) {

    }
}

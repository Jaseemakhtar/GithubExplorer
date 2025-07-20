package com.jaseem.githubexplorer.data.listscreen

import com.jaseem.githubexplorer.api.GitHubApiService
import com.jaseem.githubexplorer.api.Resource
import com.jaseem.githubexplorer.api.resourceWrapper
import com.jaseem.githubexplorer.data.common.SearchUserResponse
import com.jaseem.githubexplorer.data.common.UserSearchItemResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ListScreenRepository {
    suspend fun getAllUsers(): Resource<List<UserSearchItemResponse>>

    suspend fun searchUsers(query: String): Resource<SearchUserResponse>
}

class ListScreenRepositoryImp(
    private val api: GitHubApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ListScreenRepository {

    override suspend fun getAllUsers(): Resource<List<UserSearchItemResponse>> =
        withContext(dispatcher) {
            resourceWrapper {
                api.getAllUsers()
            }
        }

    override suspend fun searchUsers(query: String): Resource<SearchUserResponse> =
        withContext(dispatcher) {
            resourceWrapper {
                val userQuery = query.plus(" type:user")
                api.searchUsers(userQuery)
            }
        }

    suspend fun getOrgMembers(org: String) = withContext(dispatcher) {

    }
}

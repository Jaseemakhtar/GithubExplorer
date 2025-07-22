package com.jaseem.githubexplorer.data.detailscreen

import com.jaseem.githubexplorer.api.GitHubApiService
import com.jaseem.githubexplorer.api.Resource
import com.jaseem.githubexplorer.api.resourceWrapper
import com.jaseem.githubexplorer.data.common.model.UserDetailResponse
import com.jaseem.githubexplorer.data.detailscreen.model.RepositoryDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface UserDetailRepository {
    suspend fun getUserDetails(userId: Int): Resource<UserDetailResponse>

    suspend fun getUserRepositories(username: String): Resource<List<RepositoryDetail>>
}

class UserDetailRepositoryImp(
    private val api: GitHubApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserDetailRepository {

    override suspend fun getUserDetails(
        userId: Int
    ): Resource<UserDetailResponse> = withContext(dispatcher) {
        resourceWrapper {
            api.getUserDetails(userId)
        }
    }

    override suspend fun getUserRepositories(username: String): Resource<List<RepositoryDetail>> =
        withContext(dispatcher) {
            resourceWrapper {
                api.getUserRepositories(username)
            }
        }
}

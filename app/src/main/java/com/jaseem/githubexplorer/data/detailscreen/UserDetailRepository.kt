package com.jaseem.githubexplorer.data.detailscreen

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.jaseem.githubexplorer.api.GitHubApiService
import com.jaseem.githubexplorer.api.Resource
import com.jaseem.githubexplorer.api.resourceWrapper
import com.jaseem.githubexplorer.data.common.model.UserDetailResponse
import com.jaseem.githubexplorer.data.detailscreen.model.RepositoryDetail
import com.jaseem.githubexplorer.data.detailscreen.pagingsource.UnforkedRepositoryPagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface UserDetailRepository {
    suspend fun getUserDetails(userId: Int): Resource<UserDetailResponse>

    fun getUnforkedRepositories(username: String): Pager<Int, RepositoryDetail>
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

    override fun getUnforkedRepositories(username: String): Pager<Int, RepositoryDetail> =
        Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            pagingSourceFactory = { UnforkedRepositoryPagingSource(api =api, userName = username) }
        )
}

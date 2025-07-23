package com.jaseem.githubexplorer.data.listscreen

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.jaseem.githubexplorer.api.GitHubApiService
import com.jaseem.githubexplorer.data.common.model.UserSearchItemResponse
import com.jaseem.githubexplorer.data.listscreen.pagingsource.InitialTrendingUsersPagingSource
import com.jaseem.githubexplorer.data.listscreen.pagingsource.SearchUserPagingSource
import com.jaseem.githubexplorer.ui.listscreen.viewmodel.Location
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ListScreenRepository {
    suspend fun getAllUsers(): Pager<Int, UserSearchItemResponse>

    suspend fun searchUsers(query: String, location: Location): Pager<Int, UserSearchItemResponse>
}

class ListScreenRepositoryImp(
    private val api: GitHubApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ListScreenRepository {

    override suspend fun getAllUsers(): Pager<Int, UserSearchItemResponse> =
        Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            pagingSourceFactory = { InitialTrendingUsersPagingSource(api) }
        )

    override suspend fun searchUsers(
        query: String,
        location: Location
    ): Pager<Int, UserSearchItemResponse> {
        val locationFilter = if (location == Location.None) "" else "location:${location.countryName}"

        val separator = if (query.isNotEmpty() && location != Location.None) {
            "+"
        } else {
            ""
        }

        return Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            pagingSourceFactory = { SearchUserPagingSource(api, "$query$separator$locationFilter") }
        )
    }

    suspend fun getOrgMembers(org: String) = withContext(dispatcher) {

    }
}

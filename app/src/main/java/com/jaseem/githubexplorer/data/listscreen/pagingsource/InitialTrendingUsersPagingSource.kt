package com.jaseem.githubexplorer.data.listscreen.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jaseem.githubexplorer.api.GitHubApiService
import com.jaseem.githubexplorer.api.Resource
import com.jaseem.githubexplorer.api.resourceWrapper
import com.jaseem.githubexplorer.data.common.model.UserSearchItemResponse

class InitialTrendingUsersPagingSource(
    private val api: GitHubApiService
): PagingSource<Int, UserSearchItemResponse>() {

    override fun getRefreshKey(state: PagingState<Int, UserSearchItemResponse>): Int? {
        val anchorPosition = state.anchorPosition

        return anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserSearchItemResponse> {
        return try {
            val page = params.key ?: 1

            val response = resourceWrapper {
                api.getAllUsers(perPage = params.loadSize)
            }

            when(response) {
                is Resource.Error -> {
                    LoadResult.Error(response.throwable)
                }

                is Resource.Failure -> {
                    LoadResult.Error(response.throwable)
                }

                is Resource.Success -> {
                    val users = response.data

                    LoadResult.Page(
                        data = users,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (users.isEmpty()) null else page + 1
                    )
                }

            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

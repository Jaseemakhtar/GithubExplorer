package com.jaseem.githubexplorer.data.detailscreen.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jaseem.githubexplorer.api.GitHubApiService
import com.jaseem.githubexplorer.api.Resource
import com.jaseem.githubexplorer.api.resourceWrapper
import com.jaseem.githubexplorer.data.detailscreen.model.RepositoryDetail

class UnforkedRepositoryPagingSource(
    private val api: GitHubApiService,
    private val userName: String
) : PagingSource<Int, RepositoryDetail>() {

    override fun getRefreshKey(state: PagingState<Int, RepositoryDetail>): Int? {
        val anchorPosition = state.anchorPosition

        return anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryDetail> =
        try {
            val page = params.key ?: 1

            val response = resourceWrapper {
                api.getUserRepositories(
                    username = userName,
                    page = page,
                    perPage = params.loadSize
                )
            }

            when (response) {
                is Resource.Error -> {
                    LoadResult.Error(response.throwable)
                }

                is Resource.Failure -> {
                    LoadResult.Error(response.throwable)
                }

                is Resource.Success -> {
                    val repos = response.data.filter { it.fork.not() }

                    LoadResult.Page(
                        data = repos,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (repos.isEmpty()) null else page + 1
                    )
                }

            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
}
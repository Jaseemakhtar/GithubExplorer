package com.jaseem.githubexplorer.api

import com.jaseem.githubexplorer.data.common.model.SearchUserResponse
import com.jaseem.githubexplorer.data.common.model.UserDetailResponse
import com.jaseem.githubexplorer.data.common.model.UserSearchItemResponse
import com.jaseem.githubexplorer.data.detailscreen.model.RepositoryDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    //
    // https://api.github.com/users
    // https://api.github.com/orgs/moneyforward/members

    @GET("users")
    suspend fun getAllUsers(
        @Query("per_page") perPage: Int = 20
    ): Response<List<UserSearchItemResponse>>

    @GET("orgs/{org}/members")
    suspend fun getOrgMembers(@Path("org") org: String)

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 20
    ): Response<SearchUserResponse>

    @GET("user/{accountId}")
    suspend fun getUserDetails(
        @Path("accountId") accountId: Int
    ): Response<UserDetailResponse>

    @GET("users/{username}/repos")
    suspend fun getUserRepositories(
        @Path("username") username: String,
//        @Query("per_page") perPage: Int = 100
    ): Response<List<RepositoryDetail>>
}

package com.jaseem.githubexplorer.api

import com.jaseem.githubexplorer.data.common.UserSearchItemResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {
    //
    // https://api.github.com/users
    // https://api.github.com/orgs/moneyforward/members

    @GET("users")
    suspend fun getAllUsers(): Response<List<UserSearchItemResponse>>

    @GET("orgs/{org}/members")
    suspend fun getOrgMembers(@Path("org") org: String)

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Header("Authorization") token: String? = null
    )

    @GET("users/{username}")
    suspend fun getUserDetails(
        @Path("username") username: String,
        @Header("Authorization") token: String? = null
    )

    @GET("users/{username}/repos")
    suspend fun getUserRepositories(
        @Path("username") username: String,
        @Query("per_page") perPage: Int = 100,
        @Header("Authorization") token: String? = null
    )
}

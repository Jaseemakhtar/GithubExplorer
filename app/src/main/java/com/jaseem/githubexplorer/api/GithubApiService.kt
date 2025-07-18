package com.jaseem.githubexplorer.api

import com.jaseem.githubexplorer.data.common.SearchUserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Header

interface GitHubApiService {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Header("Authorization") token: String? = null
    ): Resource<SearchUserResponse>

    @GET("users/{username}")
    suspend fun getUserDetails(
        @Path("username") username: String,
        @Header("Authorization") token: String? = null
    ): Resource<UserDetailsResponse>

    @GET("users/{username}/repos")
    suspend fun getUserRepositories(
        @Path("username") username: String,
        @Query("per_page") perPage: Int = 100,
        @Header("Authorization") token: String? = null
    ): Resource<List<Repository>>
}

package com.jaseem.githubexplorer.api

import retrofit2.Response

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val throwable: Throwable) : Resource<T>()
    data class Failure<T>(val throwable: Throwable) : Resource<T>()
}

internal suspend inline fun <reified T> resourceWrapper(
    crossinline apiCall: suspend () -> Response<T>
): Resource<T> = try {
    val response = apiCall()
    if (response.isSuccessful) {
        Resource.Success(response.body()!!)
    } else {
       Resource.Error(Exception(response.message()))
    }
} catch (exp: Exception) {
    Resource.Failure(exp)
}

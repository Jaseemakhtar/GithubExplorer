package com.jaseem.githubexplorer.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import retrofit2.Response

private val json = Json {
    ignoreUnknownKeys = true
}

sealed interface Resource<T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Error<T>(val throwable: Throwable) : Resource<T>
    data class Failure<T>(val throwable: Throwable) : Resource<T>
}

internal suspend inline fun <reified T> resourceWrapper(
    crossinline apiCall: suspend () -> Response<T>
): Resource<T> = try {
    val response = apiCall()
    if (response.isSuccessful) {
        Resource.Success(response.body()!!)
    } else {
        Resource.Error(response.toException())
    }
} catch (exp: Exception) {
    Resource.Failure(exp)
}

private fun <T> Response<T>.toException(): Exception {
    return try {
        json.decodeFromString<ServerError>(errorBody()!!.string())
    } catch (_: Exception) {
        HttpException(this)
    }
}

@Serializable
data class ServerError(
    override val message: String,
    @SerialName("documentation_url")
    val documentationUrl: String? = null,
    val status: String? = null
): Exception(message)

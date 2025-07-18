package com.jaseem.githubexplorer.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

val json = Json {
    ignoreUnknownKeys = true
}

object GitHubClient {

    private const val BASE_URL = "https://api.github.com/"
    private const val PERSONAL_ACCESS_TOKEN = "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"

    fun create(): GitHubApiService {
//        val logging = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }

        val client = OkHttpClient.Builder()
//            .addInterceptor(logging)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "token $PERSONAL_ACCESS_TOKEN")
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        return retrofit.create(GitHubApiService::class.java)
    }
}

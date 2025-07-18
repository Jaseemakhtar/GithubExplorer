package com.jaseem.githubexplorer.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object GitHubClient {
    private const val BASE_URL = "https://api.github.com/"
    private const val PERSONAL_ACCESS_TOKEN = "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"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "token $PERSONAL_ACCESS_TOKEN")
                .build()
            chain.proceed(request)
        }
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val githubApiService: GitHubApiService by lazy {
        retrofit.create(GitHubApiService::class.java)
    }
}

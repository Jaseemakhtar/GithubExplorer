package com.jaseem.githubexplorer.data.detailscreen.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryDetail(
    val id: Int,
    val name: String,
    @SerialName("html_url") val htmlUrl: String,
    val description: String?,
    val fork: Boolean,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("stargazers_count") val stargazersCount: Int,
    @SerialName("watchers_count") val watchersCount: Int,
    val language: String?,
    @SerialName("forks_count") val forksCount: Int,
    val forks: Int,
    @SerialName("default_branch") val defaultBranch: String
)

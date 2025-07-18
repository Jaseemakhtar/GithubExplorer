package com.jaseem.githubexplorer.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryScreen (val userId: String)

fun NavController.navToRepositoryScreen(userId: String) {
    navigate(RepositoryScreen(userId))
}

fun NavGraphBuilder.repositoryScreen() {

}
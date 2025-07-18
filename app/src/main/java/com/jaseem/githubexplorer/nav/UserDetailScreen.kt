package com.jaseem.githubexplorer.nav

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jaseem.githubexplorer.ui.userDetailScreen.UserDetailScreen
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailRoute (val userId: String)

fun NavController.navToUserDetailScreen(userId: String) {
    navigate(UserDetailRoute(userId))
}

fun NavGraphBuilder.repositoryScreen() {
    composable<UserDetailRoute> {
        UserDetailScreen()
    }
}
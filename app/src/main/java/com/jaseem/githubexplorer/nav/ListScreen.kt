package com.jaseem.githubexplorer.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jaseem.githubexplorer.ui.listscreen.ListScreen
import kotlinx.serialization.Serializable

@Serializable
object ListScreenRoute

fun NavGraphBuilder.listScreen(
    onClickItem: (userId: Int, username: String) -> Unit
) {
    composable<ListScreenRoute> {
        ListScreen(
            onClickItem = onClickItem
        )
    }
}
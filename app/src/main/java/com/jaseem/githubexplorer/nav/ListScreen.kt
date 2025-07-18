package com.jaseem.githubexplorer.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jaseem.githubexplorer.ui.listscreen.ListScreen
import kotlinx.serialization.Serializable

@Serializable
object ListScreen

fun NavGraphBuilder.listScreen(
    onClickItem: () -> Unit
) {
    composable<ListScreen> {
        ListScreen(
            onClickItem = onClickItem
        )
    }
}
package com.jaseem.githubexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jaseem.githubexplorer.nav.ListScreenRoute
import com.jaseem.githubexplorer.nav.listScreen
import com.jaseem.githubexplorer.nav.navToUserDetailScreen
import com.jaseem.githubexplorer.nav.repositoryScreen
import com.jaseem.githubexplorer.ui.theme.GithubExplorerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubExplorerTheme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = ListScreenRoute) {
                    listScreen(
                        onClickItem = {
                            navController.navToUserDetailScreen("tempId")
                        }
                    )

                    repositoryScreen()
                }
            }
        }
    }
}

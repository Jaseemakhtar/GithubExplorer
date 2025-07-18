package com.jaseem.githubexplorer.ui.userDetailScreen

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jaseem.githubexplorer.ui.userDetailScreen.viewmodel.UserDetailScreenViewModel
import com.jaseem.githubexplorer.ui.userDetailScreen.viewmodel.UserDetailViewModelFactory

@Composable
fun UserDetailScreen(
    viewModel: UserDetailScreenViewModel = viewModel(factory = UserDetailViewModelFactory())
) {

}
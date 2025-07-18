package com.jaseem.githubexplorer.ui.listscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jaseem.githubexplorer.ui.listscreen.viewmodel.ListScreenViewModel
import com.jaseem.githubexplorer.ui.listscreen.viewmodel.ListScreenViewModelFactory
import com.jaseem.githubexplorer.ui.theme.SPACE_8

@Composable
fun ListScreen(
    onClickItem: () -> Unit,
    viewModel: ListScreenViewModel = viewModel(factory = ListScreenViewModelFactory())
) {
    LazyColumn (verticalArrangement = Arrangement.spacedBy(SPACE_8)) {
        repeat(20) {
            item {
                Text("User $it", modifier = Modifier.fillMaxWidth().height(80.dp).clickable(onClick = onClickItem))
            }
        }
    }
}
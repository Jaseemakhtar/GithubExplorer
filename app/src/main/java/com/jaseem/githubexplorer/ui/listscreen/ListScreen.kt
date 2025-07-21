package com.jaseem.githubexplorer.ui.listscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.jaseem.githubexplorer.data.common.UserSearchItemResponse
import com.jaseem.githubexplorer.ui.listscreen.viewmodel.ListScreenViewModel
import com.jaseem.githubexplorer.ui.listscreen.viewmodel.ListScreenViewModelFactory
import com.jaseem.githubexplorer.ui.state.UiState
import com.jaseem.githubexplorer.ui.theme.AccentGithubGreen
import com.jaseem.githubexplorer.ui.theme.ColumnSpacer
import com.jaseem.githubexplorer.ui.theme.DarkOnSurface
import com.jaseem.githubexplorer.ui.theme.DarkOutline
import com.jaseem.githubexplorer.ui.theme.DarkSurface
import com.jaseem.githubexplorer.ui.theme.FiraCodeFontFamily
import com.jaseem.githubexplorer.ui.theme.RowSpacer
import com.jaseem.githubexplorer.ui.theme.SIZE_48
import com.jaseem.githubexplorer.ui.theme.SPACE_16
import com.jaseem.githubexplorer.ui.theme.SPACE_8
import com.jaseem.githubexplorer.ui.theme.TextPrimary

@Composable
fun ListScreen(
    onClickItem: (userId: Int, username: String) -> Unit,
    viewModel: ListScreenViewModel = viewModel(factory = ListScreenViewModelFactory())
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        UiState.Loading, is UiState.DirtyLoading -> {

        }

        is UiState.Error -> {

        }

        is UiState.Success -> {
            val data = (uiState as UiState.Success<List<UserSearchItemResponse>>).data
            val searchQuery by viewModel.searchQuery.collectAsState()

            Column(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(color = DarkSurface)) {
                    ColumnSpacer(SPACE_16)

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = viewModel::onSearchQueryChange,
                        placeholder = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Rounded.Search,
                                    contentDescription = null
                                )

                                RowSpacer(SPACE_16)

                                Text(
                                    text = "Search users",
                                    fontFamily = FiraCodeFontFamily
                                )
                            }
                        },
                        modifier = Modifier
                            .padding(horizontal = SPACE_16)
                            .fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = DarkOnSurface,
                            unfocusedTextColor = DarkOnSurface,
                            focusedBorderColor = AccentGithubGreen,
                            focusedTextColor = TextPrimary,
                            cursorColor = TextPrimary
                        ),
                        textStyle = TextStyle(
                            fontFamily = FiraCodeFontFamily,
                            color = TextPrimary,
                            fontWeight = FontWeight.Normal
                        ),
                        maxLines = 1
                    )

                    ColumnSpacer(SPACE_16)
                }

                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(items = data) {
                        ListItem(
                            name = it.login,
                            avatarUrl = it.avatarUrl,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .clickable(
                                    onClick = {
                                        onClickItem(it.id, it.login)
                                    }
                                )
                                .padding(vertical = SPACE_8, horizontal = SPACE_16)

                        )
                    }
                }
            }
        }
    }


}

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    name: String,
    avatarUrl: String
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(avatarUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .size(SIZE_48)
                .clip(CircleShape)
                .border(width = 0.4.dp, color = DarkOutline, shape = CircleShape)
        )

        RowSpacer(SPACE_16)

        Text(
            text = name,
            fontFamily = FiraCodeFontFamily,
            color = TextPrimary,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )

        RowSpacer(SPACE_16)

        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = null,
            tint = DarkOnSurface
        )
    }
}
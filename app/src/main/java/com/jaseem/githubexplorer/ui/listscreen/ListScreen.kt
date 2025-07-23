package com.jaseem.githubexplorer.ui.listscreen

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.jaseem.githubexplorer.R
import com.jaseem.githubexplorer.ui.common.EmptyState
import com.jaseem.githubexplorer.ui.common.ErrorStateUi
import com.jaseem.githubexplorer.ui.listscreen.viewmodel.ListScreenViewModel
import com.jaseem.githubexplorer.ui.listscreen.viewmodel.ListScreenViewModelFactory
import com.jaseem.githubexplorer.ui.listscreen.viewmodel.Location
import com.jaseem.githubexplorer.ui.theme.AccentGithubGreen
import com.jaseem.githubexplorer.ui.theme.ColumnSpacer
import com.jaseem.githubexplorer.ui.theme.DarkBackground
import com.jaseem.githubexplorer.ui.theme.DarkOnBackground
import com.jaseem.githubexplorer.ui.theme.DarkOnSurface
import com.jaseem.githubexplorer.ui.theme.DarkOutline
import com.jaseem.githubexplorer.ui.theme.DarkSurface
import com.jaseem.githubexplorer.ui.theme.FiraCodeFontFamily
import com.jaseem.githubexplorer.ui.theme.RowSpacer
import com.jaseem.githubexplorer.ui.theme.SIZE_20
import com.jaseem.githubexplorer.ui.theme.SIZE_4
import com.jaseem.githubexplorer.ui.theme.SIZE_48
import com.jaseem.githubexplorer.ui.theme.SIZE_64
import com.jaseem.githubexplorer.ui.theme.SPACE_144
import com.jaseem.githubexplorer.ui.theme.SPACE_16
import com.jaseem.githubexplorer.ui.theme.SPACE_32
import com.jaseem.githubexplorer.ui.theme.SPACE_4
import com.jaseem.githubexplorer.ui.theme.SPACE_50
import com.jaseem.githubexplorer.ui.theme.SPACE_70
import com.jaseem.githubexplorer.ui.theme.SPACE_8
import com.jaseem.githubexplorer.ui.theme.TextPrimary

@Composable
fun ListScreen(
    onClickItem: (userId: Int, username: String) -> Unit,
    viewModel: ListScreenViewModel = viewModel(factory = ListScreenViewModelFactory())
) {
    val userList = viewModel.users.collectAsLazyPagingItems()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = DarkSurface)
    ) {
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
                        text = stringResource(R.string.placeholder_search_users),
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
            maxLines = 1,
            enabled = userList.loadState.refresh != LoadState.Loading || userList.loadState.append != LoadState.Loading
        )

        ColumnSpacer(SPACE_4)

        Row(verticalAlignment = Alignment.CenterVertically) {
            RowSpacer(SPACE_16)

            val selectedLocation by viewModel.selectedLocation.collectAsStateWithLifecycle()
            var expanded by remember { mutableStateOf(false) }

            Text(
                text = stringResource(R.string.label_filter),
                fontFamily = FiraCodeFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )

            Box {
                FilterChip(
                    onClick = {
                        expanded = true
                    },
                    label = {
                        Text(
                            text = if (selectedLocation == Location.None)
                                stringResource(R.string.label_location)
                            else selectedLocation.countryName,
                            fontFamily = FiraCodeFontFamily,
                            fontWeight = FontWeight.Normal
                        )
                    },
                    selected = selectedLocation != Location.None,
                    trailingIcon =
                        {
                            Icon(
                                imageVector = Icons.Rounded.ArrowDropDown,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        },
                    shape = RoundedCornerShape(SIZE_4),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = DarkOnBackground,
                        selectedLabelColor = DarkBackground,
                        selectedTrailingIconColor = DarkBackground
                    )
                )

                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    for (location in viewModel.availableLocations) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = location.countryName,
                                    fontFamily = FiraCodeFontFamily,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.padding(SPACE_8)
                                )
                            },
                            onClick = {
                                viewModel.onLocationFilterChange(location)
                                expanded = !expanded
                            },
                            contentPadding = PaddingValues()
                        )
                    }
                }
            }


            RowSpacer(SPACE_16)
        }

        ColumnSpacer(SPACE_4)

        when (val state = userList.loadState.refresh) {
            is LoadState.Error -> {
                ErrorStateUi(
                    errorText = state.error.message ?: stringResource(R.string.error_generic),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }

            LoadState.Loading -> {
                LoadingPage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }

            is LoadState.NotLoading -> {
                if (userList.itemCount == 0) {
                    EmptyState(
                        text = stringResource(R.string.text_no_results_generic),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding = PaddingValues(bottom = SPACE_144)
                    ) {
                        items(
                            userList.itemCount,
                            key = { index -> userList[index]?.id!! }
                        ) { index ->
                            val item = userList[index] ?: return@items

                            Column {
                                ListItem(
                                    name = item.login,
                                    avatarUrl = item.avatarUrl,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .clickable(
                                            onClick = {
                                                onClickItem(item.id, item.login)
                                            }
                                        )
                                        .padding(vertical = SPACE_8, horizontal = SPACE_16)
                                )

                                if (index != userList.itemCount - 1) {
                                    ListDivider()
                                }
                            }

                        }

                        item {
                            when (userList.loadState.append) {
                                LoadState.Loading -> {

                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        ListDivider()
                                        ListItemLoading(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(SIZE_64)
                                        )
                                    }
                                }

                                else -> {
                                    /* no-op */
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingPage(modifier: Modifier = Modifier) {
    LazyColumn(modifier) {
        items(20) {
            ListItemLoading(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SIZE_64)
            )

            if (it != 19) {
                ListDivider()
            }
        }
    }
}


@Composable
private fun ListDivider() {
    HorizontalDivider(
        modifier = Modifier
            .padding(start = SPACE_70, end = SPACE_50)
            .fillMaxWidth(),
        color = DarkOutline
    )
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

@Composable
fun ListItemLoading(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val color by infiniteTransition.animateColor(
        initialValue = DarkOutline,
        targetValue = DarkOutline.copy(alpha = 0.7f),
        animationSpec =
            infiniteRepeatable(
                animation = tween(600, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse,
            )
    )

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RowSpacer(SPACE_16)

        Box(
            modifier = Modifier
                .size(SIZE_48)
                .clip(CircleShape)
                .background(color)
        )

        RowSpacer(SPACE_16)

        Box(
            modifier = Modifier
                .background(color, RoundedCornerShape(SIZE_4))
                .height(SIZE_20)
                .weight(1f)
        )

        RowSpacer(SPACE_32)
    }
}

package com.jaseem.githubexplorer.ui.userDetailScreen

import android.content.Intent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.jaseem.githubexplorer.R
import com.jaseem.githubexplorer.data.common.model.UserDetailResponse
import com.jaseem.githubexplorer.ui.common.EmptyState
import com.jaseem.githubexplorer.ui.common.ErrorStateUi
import com.jaseem.githubexplorer.ui.state.UiState
import com.jaseem.githubexplorer.ui.theme.ColumnSpacer
import com.jaseem.githubexplorer.ui.theme.DarkOnBackground
import com.jaseem.githubexplorer.ui.theme.DarkOutline
import com.jaseem.githubexplorer.ui.theme.DarkPrimary
import com.jaseem.githubexplorer.ui.theme.DarkSurface
import com.jaseem.githubexplorer.ui.theme.FiraCodeFontFamily
import com.jaseem.githubexplorer.ui.theme.RowSpacer
import com.jaseem.githubexplorer.ui.theme.SIZE_100
import com.jaseem.githubexplorer.ui.theme.SIZE_12
import com.jaseem.githubexplorer.ui.theme.SIZE_16
import com.jaseem.githubexplorer.ui.theme.SIZE_24
import com.jaseem.githubexplorer.ui.theme.SIZE_4
import com.jaseem.githubexplorer.ui.theme.SIZE_48
import com.jaseem.githubexplorer.ui.theme.SPACE_12
import com.jaseem.githubexplorer.ui.theme.SPACE_144
import com.jaseem.githubexplorer.ui.theme.SPACE_16
import com.jaseem.githubexplorer.ui.theme.SPACE_4
import com.jaseem.githubexplorer.ui.theme.SPACE_6
import com.jaseem.githubexplorer.ui.theme.SPACE_8
import com.jaseem.githubexplorer.ui.theme.languageColorMap
import com.jaseem.githubexplorer.ui.userDetailScreen.viewmodel.UserDetailScreenViewModel
import com.jaseem.githubexplorer.ui.userDetailScreen.viewmodel.UserDetailViewModelFactory

@Composable
fun UserDetailScreen(
    viewModel: UserDetailScreenViewModel = viewModel(factory = UserDetailViewModelFactory())
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val repositories = viewModel.repositories.collectAsLazyPagingItems()
    val context = LocalContext.current

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkSurface)
        ) {
            when (uiState) {
                UiState.Loading -> {
                    ProfileSectionLoadingShimmer()
                }

                is UiState.Error -> {
                    ErrorStateUi(modifier = Modifier.fillMaxSize())
                }

                is UiState.Success -> {
                    ProfileSection(
                        userDetails = (uiState as UiState.Success<UserDetailResponse>).data,
                        modifier = Modifier.padding(horizontal = SPACE_16)
                    )
                }

            }

        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (repositories.loadState.refresh) {
                is LoadState.Error -> {
                    ErrorStateUi(
                        errorText = stringResource(R.string.text_repos_error),
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                LoadState.Loading -> {
                    RepoSectionLoadingShimmer()
                }

                is LoadState.NotLoading -> {
                    if (repositories.itemCount == 0) {
                        EmptyState(
                            text = stringResource(R.string.text_no_repos),
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(1f)
                        ) {

                            items(repositories.itemCount) {
                                val item = repositories[it] ?: return@items

                                Column {

                                    RepositoryListItem(
                                        name = item.name,
                                        desc = item.description,
                                        language = item.language,
                                        stars = item.stargazersCount,
                                        forks = item.forks,
                                        modifier = Modifier.clickable {

                                            val intent = Intent(Intent.ACTION_VIEW)
                                            intent.data = item.htmlUrl.toUri()
                                            context.startActivity(intent)

                                        }
                                    )

                                    if (it != repositories.itemCount - 1) {
                                        ListItemDivider()
                                    }
                                }
                            }


                            when (repositories.loadState.append) {
                                LoadState.Loading -> {
                                    item {
                                        Column(modifier = Modifier.fillMaxWidth()) {
                                            ListItemDivider()
                                            LoadingListItem(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = SPACE_16)
                                            )
                                        }
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

    LaunchedEffect(Unit) {
        viewModel.init()
    }
}

@Composable
private fun ProfileSection(
    userDetails: UserDetailResponse,
    modifier: Modifier = Modifier
) {
    Column(modifier) {

        Row {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(userDetails.avatarUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(SIZE_100)
                    .clip(CircleShape)
                    .border(
                        width = 0.4.dp,
                        color = DarkOutline,
                        shape = CircleShape
                    )
            )

            RowSpacer(SPACE_16)

            Column {
                userDetails.name?.let {
                    Text(
                        text = it,
                        fontFamily = FiraCodeFontFamily,
                        color = DarkOnBackground,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                userDetails.bio?.let {
                    ColumnSpacer(SPACE_8)

                    Text(
                        text = it,
                        fontFamily = FiraCodeFontFamily,
                        color = DarkOnBackground,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                userDetails.location?.let {
                    ColumnSpacer(SPACE_6)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val color = DarkOnBackground.copy(alpha = 0.8f)

                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = null,
                            tint = color,
                            modifier = Modifier.size(SIZE_16)
                        )

                        RowSpacer(SPACE_4)

                        Text(
                            text = it,
                            fontFamily = FiraCodeFontFamily,
                            color = color,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            RowSpacer(SPACE_16)
        }

        ColumnSpacer(SPACE_16)

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ProfileVerticalInfoItem(
                title = "${userDetails.publicRepos}",
                desc = stringResource(R.string.label_repos),
                modifier = Modifier.wrapContentSize()
            )

            VerticalDivider(modifier = Modifier.height(SIZE_48), color = DarkOutline)

            ProfileVerticalInfoItem(
                title = "${userDetails.followers}",
                desc = stringResource(R.string.label_followers),
                modifier = Modifier.wrapContentSize()
            )

            VerticalDivider(modifier = Modifier.height(SIZE_48), color = DarkOutline)

            ProfileVerticalInfoItem(
                title = "${userDetails.following}",
                desc = stringResource(R.string.label_following),
                modifier = Modifier.wrapContentSize()
            )
        }

        ColumnSpacer(SPACE_16)
    }
}

@Composable
fun ListItemDivider() {
    HorizontalDivider(
        modifier = Modifier
            .padding(horizontal = SPACE_16)
            .fillMaxWidth(),
        color = DarkOutline
    )
}

@Composable
fun ProfileVerticalInfoItem(
    title: String,
    desc: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontFamily = FiraCodeFontFamily,
            color = DarkOnBackground,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp
        )

        Text(
            text = desc,
            fontFamily = FiraCodeFontFamily,
            color = DarkOnBackground.copy(alpha = 0.8f),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        )
    }
}


@Composable
fun RepositoryListItem(
    name: String,
    desc: String?,
    language: String?,
    stars: Int,
    forks: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        ColumnSpacer(SPACE_12)

        val commonModifier = remember {
            Modifier
                .fillMaxWidth()
                .padding(horizontal = SPACE_16)
        }

        Text(
            text = name,
            fontFamily = FiraCodeFontFamily,
            color = DarkPrimary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            modifier = commonModifier,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        if (desc != null) {
            ColumnSpacer(SPACE_4)

            Text(
                text = desc,
                fontFamily = FiraCodeFontFamily,
                color = DarkOnBackground.copy(alpha = 0.8f),
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                modifier = commonModifier,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

        ColumnSpacer(SPACE_4)

        Row(modifier = commonModifier, verticalAlignment = Alignment.CenterVertically) {
            if (language != null) {
                Box(
                    modifier = Modifier
                        .size(SIZE_16)
                        .background(
                            color = languageColorMap[language] ?: DarkPrimary,
                            shape = CircleShape
                        )
                )

                RowSpacer(SPACE_4)

                Text(
                    text = language,
                    fontFamily = FiraCodeFontFamily,
                    color = DarkOnBackground.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )

                RowSpacer(SPACE_8)
            }

            Icon(
                painter = painterResource(R.drawable.ic_star),
                contentDescription = null,
                tint = DarkOnBackground.copy(alpha = 0.8f),
                modifier = Modifier.size(SIZE_16)
            )

            RowSpacer(SPACE_4)

            Text(
                text = "$stars",
                fontFamily = FiraCodeFontFamily,
                color = DarkOnBackground.copy(alpha = 0.8f),
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )


            RowSpacer(SPACE_8)

            Icon(
                painter = painterResource(R.drawable.ic_fork),
                contentDescription = null,
                tint = DarkOnBackground.copy(alpha = 0.8f),
                modifier = Modifier.size(SIZE_16)
            )

            RowSpacer(SPACE_4)

            Text(
                text = "$forks",
                fontFamily = FiraCodeFontFamily,
                color = DarkOnBackground.copy(alpha = 0.8f),
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
        }

        ColumnSpacer(SPACE_12)
    }
}

@Composable
private fun LoadingListItem(modifier: Modifier = Modifier) {
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

    Column(
        modifier = modifier
    ) {
        ColumnSpacer(SPACE_16)

        Box(
            modifier = Modifier
                .background(color, RoundedCornerShape(SIZE_4))
                .height(SIZE_24)
                .fillMaxWidth(0.6f)
        )

        ColumnSpacer(SPACE_8)

        Box(
            modifier = Modifier
                .background(color, RoundedCornerShape(SIZE_4))
                .height(SIZE_16)
                .fillMaxWidth(0.8f)
        )

        ColumnSpacer(SPACE_4)

        Box(
            modifier = Modifier
                .background(color, RoundedCornerShape(SIZE_4))
                .height(SIZE_12)
                .fillMaxWidth(0.4f)
        )

        ColumnSpacer(SPACE_16)
    }
}

@Composable
private fun ProfileSectionLoadingShimmer(modifier: Modifier = Modifier) {
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

    Column(modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RowSpacer(SPACE_16)

            Box(
                modifier = Modifier
                    .size(SIZE_100)
                    .background(color, CircleShape)
            )

            RowSpacer(SPACE_16)

            Column {
                Box(
                    modifier = Modifier
                        .background(color, RoundedCornerShape(SIZE_4))
                        .height(SIZE_24)
                        .fillMaxWidth(0.6f)
                )

                ColumnSpacer(SPACE_12)

                Box(
                    modifier = Modifier
                        .background(color, RoundedCornerShape(SIZE_4))
                        .height(SIZE_16)
                        .fillMaxWidth(0.7f)
                )

                ColumnSpacer(SPACE_12)

                Box(
                    modifier = Modifier
                        .background(color, RoundedCornerShape(SIZE_4))
                        .height(SIZE_16)
                        .fillMaxWidth(0.4f)
                )
            }

            RowSpacer(SPACE_16)
        }

        ColumnSpacer(SPACE_16)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SPACE_16),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier
                    .background(color, RoundedCornerShape(SIZE_4))
                    .height(48.dp)
                    .width(80.dp)
            )

            VerticalDivider(modifier = Modifier.height(SIZE_48), color = DarkOutline)

            Box(
                modifier = Modifier
                    .background(color, RoundedCornerShape(SIZE_4))
                    .height(48.dp)
                    .width(80.dp)
            )

            VerticalDivider(modifier = Modifier.height(SIZE_48), color = DarkOutline)

            Box(
                modifier = Modifier
                    .background(color, RoundedCornerShape(SIZE_4))
                    .height(48.dp)
                    .width(80.dp)
            )
        }

        ColumnSpacer(SPACE_16)
    }
}

@Composable
private fun RepoSectionLoadingShimmer(modifier: Modifier = Modifier) {
    LazyColumn(modifier, contentPadding = PaddingValues(bottom = SPACE_144)) {
        items(20) {
            LoadingListItem(
                modifier = Modifier
                    .padding(horizontal = SPACE_16)
                    .fillMaxWidth()
            )

            if (it != 19) {
                ListItemDivider()
            }
        }
    }
}

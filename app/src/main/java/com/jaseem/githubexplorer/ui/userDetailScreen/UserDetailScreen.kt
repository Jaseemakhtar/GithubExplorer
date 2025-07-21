package com.jaseem.githubexplorer.ui.userDetailScreen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.jaseem.githubexplorer.R
import com.jaseem.githubexplorer.data.common.UserDetailResponse
import com.jaseem.githubexplorer.data.detailscreen.model.RepositoryDetail
import com.jaseem.githubexplorer.ui.state.UiState
import com.jaseem.githubexplorer.ui.theme.ColumnSpacer
import com.jaseem.githubexplorer.ui.theme.DarkBackground
import com.jaseem.githubexplorer.ui.theme.DarkOnBackground
import com.jaseem.githubexplorer.ui.theme.DarkOnSurface
import com.jaseem.githubexplorer.ui.theme.DarkOutline
import com.jaseem.githubexplorer.ui.theme.DarkPrimary
import com.jaseem.githubexplorer.ui.theme.DarkSurface
import com.jaseem.githubexplorer.ui.theme.FiraCodeFontFamily
import com.jaseem.githubexplorer.ui.theme.RowSpacer
import com.jaseem.githubexplorer.ui.theme.SIZE_16
import com.jaseem.githubexplorer.ui.theme.SIZE_164
import com.jaseem.githubexplorer.ui.theme.SIZE_48
import com.jaseem.githubexplorer.ui.theme.SPACE_12
import com.jaseem.githubexplorer.ui.theme.SPACE_144
import com.jaseem.githubexplorer.ui.theme.SPACE_16
import com.jaseem.githubexplorer.ui.theme.SPACE_4
import com.jaseem.githubexplorer.ui.theme.SPACE_48
import com.jaseem.githubexplorer.ui.theme.SPACE_6
import com.jaseem.githubexplorer.ui.theme.SPACE_8
import com.jaseem.githubexplorer.ui.theme.languageColorMap
import com.jaseem.githubexplorer.ui.userDetailScreen.viewmodel.UserDetailScreenViewModel
import com.jaseem.githubexplorer.ui.userDetailScreen.viewmodel.UserDetailViewModelFactory

@Composable
fun UserDetailScreen(
    viewModel: UserDetailScreenViewModel = viewModel(factory = UserDetailViewModelFactory())
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        UiState.Loading, is UiState.DirtyLoading -> {

        }

        is UiState.Error -> {

        }

        is UiState.Success -> {
            val data =
                (uiState as UiState.Success<Pair<UserDetailResponse, List<RepositoryDetail>>>).data

            val userDetails = data.first
            val userRepos = data.second

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkBackground)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = DarkSurface),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ColumnSpacer(SPACE_16)

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(userDetails.avatarUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(SIZE_164)
                            .clip(CircleShape)
                            .border(width = 0.4.dp, color = DarkOutline, shape = CircleShape)
                    )

                    userDetails.name?.let {
                        ColumnSpacer(SPACE_16)

                        Text(
                            text = it,
                            fontFamily = FiraCodeFontFamily,
                            color = DarkOnBackground,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 24.sp
                        )
                    }

                    userDetails.bio?.let {
                        ColumnSpacer(SPACE_12)

                        Text(
                            text = it,
                            fontFamily = FiraCodeFontFamily,
                            color = DarkOnBackground,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = SPACE_16)
                                .fillMaxWidth()
                        )
                    }

                    userDetails.location?.let {
                        ColumnSpacer(SPACE_12)

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val color = DarkOnBackground.copy(alpha = 0.8f)

                            Icon(
                                imageVector = Icons.Rounded.LocationOn,
                                contentDescription = null,
                                tint = color,
                                modifier = Modifier.size(SIZE_16)
                            )

                            RowSpacer(SPACE_8)

                            Text(
                                text = it,
                                fontFamily = FiraCodeFontFamily,
                                color = color,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp
                            )
                        }
                    }

                    ColumnSpacer(SPACE_16)
                }

                Box(Modifier.fillMaxWidth().height(SPACE_16).background(DarkSurface))

                Row(modifier = Modifier.fillMaxWidth().background(DarkSurface)) {
                    RowSpacer(SPACE_16)

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${userDetails.publicRepos}",
                            fontFamily = FiraCodeFontFamily,
                            color = DarkOnBackground,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 24.sp
                        )

                        ColumnSpacer(SPACE_6)

                        Text(
                            text = "Repos",
                            fontFamily = FiraCodeFontFamily,
                            color = DarkOnBackground.copy(alpha = 0.8f),
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }

                    RowSpacer(SPACE_16)

                    VerticalDivider(modifier = Modifier.height(SIZE_48), color = DarkOnSurface)

                    RowSpacer(SPACE_16)

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${userDetails.followers}",
                            fontFamily = FiraCodeFontFamily,
                            color = DarkOnBackground,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 24.sp
                        )

                        ColumnSpacer(SPACE_6)

                        Text(
                            text = "Followers",
                            fontFamily = FiraCodeFontFamily,
                            color = DarkOnBackground.copy(alpha = 0.8f),
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }

                    RowSpacer(SPACE_16)

                    VerticalDivider(modifier = Modifier.height(SIZE_48), color = DarkOnSurface)

                    RowSpacer(SPACE_16)

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${userDetails.following}",
                            fontFamily = FiraCodeFontFamily,
                            color = DarkOnBackground,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 24.sp
                        )

                        ColumnSpacer(SPACE_6)

                        Text(
                            text = "Following",
                            fontFamily = FiraCodeFontFamily,
                            color = DarkOnBackground.copy(alpha = 0.8f),
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }

                    RowSpacer(SPACE_16)
                }

                Box(Modifier.fillMaxWidth().height(SPACE_16).background(DarkSurface))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(bottom = SPACE_144)
                ) {
                    itemsIndexed(
                        items = userRepos,
                        key = { index, item ->
                            item.id
                        }
                    ) { index, item ->
                        val context = LocalContext.current

                        Column (
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .clickable(
                                    onClick = {
                                        val intent = Intent(Intent.ACTION_VIEW)
                                        intent.data = item.htmlUrl.toUri()
                                        context.startActivity(intent)
                                    }
                                )
                        ) {
                            RepositoryListItem(
                                name = item.name,
                                desc = item.description,
                                language = item.language,
                                stars = item.stargazersCount,
                                forks = item.forks
                            )

                            if (index != userRepos.lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = SPACE_16).fillMaxWidth(),
                                    color = DarkOutline
                                )
                            }
                        }
                    }
                }
            }
        }
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

        val commonModifier = remember { Modifier
            .fillMaxWidth()
            .padding(horizontal = SPACE_16) }

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

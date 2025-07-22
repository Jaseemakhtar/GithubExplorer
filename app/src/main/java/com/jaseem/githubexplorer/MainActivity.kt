package com.jaseem.githubexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jaseem.githubexplorer.nav.ListScreenRoute
import com.jaseem.githubexplorer.nav.listScreen
import com.jaseem.githubexplorer.nav.navToUserDetailScreen
import com.jaseem.githubexplorer.nav.repositoryScreen
import com.jaseem.githubexplorer.ui.theme.DarkBackground
import com.jaseem.githubexplorer.ui.theme.DarkSurface
import com.jaseem.githubexplorer.ui.theme.FiraCodeFontFamily
import com.jaseem.githubexplorer.ui.theme.GithubExplorerTheme
import com.jaseem.githubexplorer.ui.theme.RowSpacer
import com.jaseem.githubexplorer.ui.theme.SIZE_24
import com.jaseem.githubexplorer.ui.theme.SIZE_56
import com.jaseem.githubexplorer.ui.theme.SPACE_12
import com.jaseem.githubexplorer.ui.theme.SPACE_16
import com.jaseem.githubexplorer.ui.theme.TextPrimary
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubExplorerTheme {
                val navController = rememberNavController()
                Scaffold { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .background(color = DarkBackground)
                    ) {
                        var isHomeScreen: Boolean by remember { mutableStateOf(true) }

                        Header(
                            isHome = isHomeScreen,
                            modifier = Modifier
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = SIZE_56)
                                .background(color = DarkSurface),
                            onClickBack = {
                                navController.popBackStack()
                            }
                        )

                        NavHost(navController, startDestination = ListScreenRoute) {
                            listScreen(
                                onClickItem = { userId, username ->
                                    navController.navToUserDetailScreen(userId, username)
                                }
                            )

                            repositoryScreen()
                        }

                        LaunchedEffect(Unit) {
                            coroutineScope {
                                navController.currentBackStackEntryFlow.collect {
                                    isHomeScreen = it.destination.hasRoute(ListScreenRoute::class)
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
fun Header(
    isHome: Boolean,
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val translationOutAnimatable = remember { Animatable(0f) }
    val translationInAnimatable = remember { Animatable(1f) }
    val alphaAnimatable = remember { Animatable(1f) }
    val alphaInAnimatable = remember { Animatable(0f) }

    Row(modifier, verticalAlignment = Alignment.CenterVertically) {

        Box(modifier = Modifier.defaultMinSize(minHeight = SIZE_56, minWidth = SIZE_56), contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = null,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = false, radius = SIZE_24),
                    onClick = onClickBack
                ).graphicsLayer {
                    translationX = SPACE_12.toPx() * translationInAnimatable.value
                    alpha = alphaInAnimatable.value
                }
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_github),
                contentDescription = null,
                modifier = Modifier.graphicsLayer {
                    translationX = SPACE_12.toPx() * translationOutAnimatable.value
                    alpha = alphaAnimatable.value
                }
            )

        }

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                    append(stringResource(R.string.label_github))
                }
                withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                    append(stringResource(R.string.label_explorer))
                }
            },
            fontFamily = FiraCodeFontFamily,
            color = TextPrimary,
            modifier = Modifier.fillMaxWidth().graphicsLayer {
                alpha = alphaAnimatable.value
            }
        )

        RowSpacer(SPACE_12)


        RowSpacer(SPACE_16)
    }

    LaunchedEffect(isHome) {
        translationOutAnimatable.stop()
        if (isHome) {
            coroutineScope.launch {
                translationOutAnimatable.animateTo(0f)
            }
            coroutineScope.launch {
                alphaAnimatable.animateTo(1f)
            }
            coroutineScope.launch {
                translationInAnimatable.animateTo(1f)
            }
            coroutineScope.launch {
                alphaInAnimatable.animateTo(0f)
            }
        } else {
            coroutineScope.launch {
                translationOutAnimatable.animateTo(-1f)
            }
            coroutineScope.launch {
                alphaAnimatable.animateTo(0f)
            }
            coroutineScope.launch {
                translationInAnimatable.animateTo(0f)
            }
            coroutineScope.launch {
                alphaInAnimatable.animateTo(1f)
            }
        }
    }
}

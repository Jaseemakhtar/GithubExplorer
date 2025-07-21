package com.jaseem.githubexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
import com.jaseem.githubexplorer.ui.theme.SIZE_32
import com.jaseem.githubexplorer.ui.theme.SPACE_12
import com.jaseem.githubexplorer.ui.theme.SPACE_16
import com.jaseem.githubexplorer.ui.theme.SPACE_4
import com.jaseem.githubexplorer.ui.theme.SPACE_8
import com.jaseem.githubexplorer.ui.theme.SecondaryBg
import com.jaseem.githubexplorer.ui.theme.TextPrimary

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubExplorerTheme {
                val navController = rememberNavController()
                Scaffold { innerPadding ->
                    Column (
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .background(color = DarkBackground)
                    ) {
                        Header(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .background(color = DarkSurface)
                                .padding(vertical = SPACE_8)
                        )

                        NavHost(navController, startDestination = ListScreenRoute) {
                            listScreen(
                                onClickItem = { userId, username ->
                                    navController.navToUserDetailScreen(userId, username)
                                }
                            )

                            repositoryScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Header(modifier: Modifier = Modifier) {
    Row (modifier, verticalAlignment = Alignment.CenterVertically) {
        RowSpacer(SPACE_12)

        Row (modifier = Modifier
            .wrapContentSize()
            .background(SecondaryBg, RoundedCornerShape(SIZE_32))
            .padding(vertical = SPACE_4),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_github), contentDescription = null)

            RowSpacer(SPACE_12)

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                        append("Github")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                        append("Explorer")
                    }
                },
                fontFamily = FiraCodeFontFamily,
                color = TextPrimary
            )

            RowSpacer(SPACE_12)
        }

        RowSpacer(SPACE_16)
    }
}

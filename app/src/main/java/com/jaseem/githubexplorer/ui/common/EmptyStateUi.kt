package com.jaseem.githubexplorer.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.jaseem.githubexplorer.ui.components.RotatingCube
import com.jaseem.githubexplorer.ui.theme.ColumnSpacer
import com.jaseem.githubexplorer.ui.theme.DarkOnSurface
import com.jaseem.githubexplorer.ui.theme.FiraCodeFontFamily
import com.jaseem.githubexplorer.ui.theme.SIZE_100
import com.jaseem.githubexplorer.ui.theme.SPACE_16
import com.jaseem.githubexplorer.ui.theme.TextPrimary

@Composable
fun EmptyState(
    modifier: Modifier = Modifier,
    text: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        RotatingCube(
            color = DarkOnSurface,
            modifier = Modifier.size(SIZE_100)
        )

        ColumnSpacer(SPACE_16)

        Text(
            text = text,
            fontFamily = FiraCodeFontFamily,
            color = TextPrimary,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

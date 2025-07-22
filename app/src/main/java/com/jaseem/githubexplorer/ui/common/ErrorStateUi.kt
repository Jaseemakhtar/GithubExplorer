package com.jaseem.githubexplorer.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.jaseem.githubexplorer.R
import com.jaseem.githubexplorer.ui.theme.DarkOnBackground
import com.jaseem.githubexplorer.ui.theme.FiraCodeFontFamily
import com.jaseem.githubexplorer.ui.theme.SPACE_16
import com.jaseem.githubexplorer.ui.theme.TextPrimary

@Composable
fun ErrorStateUi(
    modifier: Modifier = Modifier,
    errorText: String = stringResource(R.string.error_generic),
    fontSize: TextUnit = 16.sp
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.Warning,
            contentDescription = null,
            tint = DarkOnBackground,
            modifier = Modifier
                .fillMaxSize(0.2f)
                .aspectRatio(1f)
        )

        Text(
            text = errorText,
            color = TextPrimary,
            fontFamily = FiraCodeFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(horizontal = SPACE_16)
        )
    }
}
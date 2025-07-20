package com.jaseem.githubexplorer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jaseem.githubexplorer.R

val FiraCodeFontFamily = FontFamily(
    Font(R.font.firacode_regular, FontWeight.Normal),
    Font(R.font.firacode_medium, FontWeight.Medium),
    Font(R.font.firacode_semibold, FontWeight.SemiBold),
    Font(R.font.firacode_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    // Display Large - Not typically used in GitHub style, but included for completeness
    displayLarge = TextStyle(
        fontFamily = FiraCodeFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    // Display Medium
    displayMedium = TextStyle(
        fontFamily = FiraCodeFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp
    ),
    // Display Small
    displaySmall = TextStyle(
        fontFamily = FiraCodeFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    // Headline Large - e.g., Repository Name
    headlineLarge = TextStyle(
        fontFamily = FiraCodeFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp
    ),
    // Headline Medium - e.g., Section Titles
    headlineMedium = TextStyle(
        fontFamily = FiraCodeFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp
    ),
    // Headline Small - e.g., Sub-section Titles
    headlineSmall = TextStyle(
        fontFamily = FiraCodeFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    // Title Large - e.g., Larger text within components
    titleLarge = TextStyle(
        fontFamily = FiraCodeFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    // Title Medium - e.g., Item titles in lists
    titleMedium = TextStyle(
        fontFamily = FiraCodeFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    // Title Small - e.g., Smaller item titles or labels
    titleSmall = TextStyle(
        fontFamily = FiraCodeFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    // Body Large - Default body text
    bodyLarge = TextStyle(
        fontFamily = FiraCodeFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    // Body Medium - Slightly smaller body text, can also be used for code blocks with FiraCode
    bodyMedium = TextStyle(
        fontFamily = FiraCodeFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp
    ),
    // Body Small - Captions, metadata
    bodySmall = TextStyle(
        fontFamily = FiraCodeFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
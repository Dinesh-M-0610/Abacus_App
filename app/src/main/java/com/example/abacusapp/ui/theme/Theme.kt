package com.example.abacusapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val CustomColorScheme = lightColorScheme(
    primary = Color.Black,  // Buttons and interactive elements in black
    secondary = Color.DarkGray,
    tertiary = Color.Gray,
    background = Color.White, // Always white background
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.Black, // Text and icons in black
    onSurface = Color.Black
)

@Composable
fun AbacusAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CustomColorScheme,
        typography = Typography,
        content = content
    )
}

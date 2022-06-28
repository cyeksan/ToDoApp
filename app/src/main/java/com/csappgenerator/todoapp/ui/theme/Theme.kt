package com.csappgenerator.todoapp.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = DarkBackground,
    primaryVariant = DarkVariant,
    secondary = DarkAccent,
    background = DarkBackground,
    surface = White,
    onPrimary = White,
    onSecondary = White,
    onBackground = DarkAccent,
    onSurface = White,

)

@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = LightBackground,
    primaryVariant = LightVariant,
    secondary = LightAccent,
    background = LightBackground,
    surface = DarkBackground,
    onPrimary = DarkBackground,
    onSecondary = LightBackground,
    onBackground = LightAccent,
    onSurface = DarkBackground,

)

@Composable
fun ToDoAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
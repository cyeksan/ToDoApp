package com.csappgenerator.todoapp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color

val DarkBackground = Color(0xFF1B1B1F)
val DarkVariant = Color(0xFF323235)
val DarkAccent = Color(0xFF75798E)
val White = Color(0xFFF3F0F5)
val LightBackground = Color(0xFFF2F0F4)
val LightVariant = Color(0xFFF5F3F6)
val LightAccent = Color(0xFF4F5B92)

val LowPriorityColor = Color(0xFF09C980)
val MediumPriorityColor = Color(0xFFFFC114)
val HighPriorityColor = Color(0xFFFF4646)


val SplashBackground: Color
    @Composable
    @ReadOnlyComposable
    get() = if (MaterialTheme.colors.isLight) LightAccent else DarkBackground


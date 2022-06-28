package com.csappgenerator.todoapp.util

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.csappgenerator.todoapp.ui.theme.*

enum class Priority {
    LOW,
    MEDIUM,
    HIGH,
    NONE;

    val color: Color
        @Composable
        @ReadOnlyComposable
        get() = when(this) {
            NONE -> MaterialTheme.colors.onSurface
            LOW -> LowPriorityColor
            MEDIUM -> MediumPriorityColor
            HIGH -> HighPriorityColor
        }
}
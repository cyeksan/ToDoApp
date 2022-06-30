package com.csappgenerator.todoapp.util

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.csappgenerator.todoapp.presentation.common.composable.PriorityItem
import com.csappgenerator.todoapp.ui.theme.HighPriorityColor
import com.csappgenerator.todoapp.ui.theme.LowPriorityColor
import com.csappgenerator.todoapp.ui.theme.MediumPriorityColor

enum class Priority// custom constructors
    (var orderType: OrderType) {
    LOW(OrderType.Ascending),
    MEDIUM(OrderType.None),
    HIGH(OrderType.Descending),
    NONE(OrderType.None);

    fun toOrderType(): OrderType {
        return orderType
    }

    val color: Color
        @Composable
        @ReadOnlyComposable
        get() = when (this) {
            NONE -> MaterialTheme.colors.onSurface
            LOW -> LowPriorityColor
            MEDIUM -> MediumPriorityColor
            HIGH -> HighPriorityColor
        }
}
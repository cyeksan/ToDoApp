package com.csappgenerator.todoapp.presentation.common.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.csappgenerator.todoapp.ui.theme.PRIORITY_INDICATOR_SIZE
import com.csappgenerator.todoapp.ui.theme.SMALL_PADDING
import com.csappgenerator.todoapp.ui.theme.Typography
import com.csappgenerator.todoapp.util.Priority


@Composable
fun PriorityItem(priority: Priority) {
    val circleColor: Color = priority.color
    Row(verticalAlignment = Alignment.CenterVertically) {
        Canvas(
            modifier = Modifier
                .size(PRIORITY_INDICATOR_SIZE)
        ) {
            drawCircle(color = circleColor)
        }

        Text(
            text = priority.name,
            modifier = Modifier
                .padding(start = SMALL_PADDING),
            style = Typography.subtitle2,
        )
    }
}

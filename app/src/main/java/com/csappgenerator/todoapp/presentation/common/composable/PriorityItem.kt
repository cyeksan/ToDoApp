package com.csappgenerator.todoapp.presentation.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.csappgenerator.todoapp.domain.model.Priority
import com.csappgenerator.todoapp.ui.theme.MEDIUM_PADDING
import com.csappgenerator.todoapp.ui.theme.PRIORITY_INDICATOR_SIZE
import com.csappgenerator.todoapp.ui.theme.Typography


@Composable
fun PriorityItem(priority: Priority) {
    val circleColor : Color = priority.color
    Row(verticalAlignment = Alignment.CenterVertically) {
        Canvas(modifier = Modifier
            .size(PRIORITY_INDICATOR_SIZE)
        ) {
            drawCircle(color = circleColor)
        }

        Text(
            text = priority.name,
            modifier = Modifier
                .padding(start = MEDIUM_PADDING),
            style = Typography.subtitle1,
        )
    }
}

@Composable
@Preview
fun PriorityItemPreview() {
    PriorityItem(priority = Priority.HIGH)
}
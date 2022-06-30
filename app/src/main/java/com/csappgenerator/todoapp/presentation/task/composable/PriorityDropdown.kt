package com.csappgenerator.todoapp.presentation.task.composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import com.csappgenerator.todoapp.R
import com.csappgenerator.todoapp.util.Priority
import com.csappgenerator.todoapp.presentation.common.composable.PriorityItem
import com.csappgenerator.todoapp.ui.theme.*

@Composable
fun PriorityDropdown(
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    val dropdownIconRotateAngle: Float by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f
    )

    var parentSize by remember { mutableStateOf(IntSize.Zero)}

    val circleColor = priority.color

    Row(modifier = Modifier
        .fillMaxWidth()
        .onGloballyPositioned {
            parentSize = it.size
        }
        .height(PRIORITY_DROPDOWN_HEIGHT)
        .clickable { expanded = true }
        .border(
            width = BORDER_WIDTH,
            color = MaterialTheme.colors.onSurface.copy(ContentAlpha.disabled),
            shape = MaterialTheme.shapes.small
        )
        .padding(start = SMALL_PADDING),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier
                .size(PRIORITY_INDICATOR_SIZE)
                .weight(1f),
            onDraw = {
                drawCircle(
                    color = circleColor
                )
            })
        Text(
            text = priority.name,
            modifier = Modifier
                .weight(8f),
            style = Typography.subtitle2,
        )
        IconButton(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .rotate(dropdownIconRotateAngle)
                .weight(1.5f),
            onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = stringResource(R.string.arrow_dropdown)
            )
        }

        DropdownMenu(
            modifier = Modifier
                .width(with(LocalDensity.current) {parentSize.width.toDp()})
                .background(
                    color = MaterialTheme.colors.primaryVariant
                ),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }) {
            Priority.values().slice(0..2).forEach { priority ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onPrioritySelected(priority)
                }) {
                    PriorityItem(priority = priority)
                }
            }

        }
    }
}

@Composable
@Preview
fun PriorityDropdownPreview() {
    PriorityDropdown(
        priority = Priority.HIGH,
        onPrioritySelected = {}
    )
}
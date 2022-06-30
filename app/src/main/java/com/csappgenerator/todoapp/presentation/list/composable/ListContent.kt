package com.csappgenerator.todoapp.presentation.list.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.csappgenerator.todoapp.domain.model.ToDoTask
import com.csappgenerator.todoapp.ui.theme.MEDIUM_PADDING
import com.csappgenerator.todoapp.ui.theme.PRIORITY_INDICATOR_SIZE
import com.csappgenerator.todoapp.ui.theme.TASK_ITEM_ELEVATION
import com.csappgenerator.todoapp.util.Constants
import com.csappgenerator.todoapp.util.Constants.DELETE_ICON_MAX_ROTATION
import com.csappgenerator.todoapp.util.Constants.LIST_SHRINK_TWEEN_DURATION
import com.csappgenerator.todoapp.util.clickableSingle

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListContent(
    taskList: List<ToDoTask>,
    onSwipeToDelete: (ToDoTask) -> Unit,
    navigateToTaskScreen: (Int) -> Unit,
) {
    if (taskList.isNotEmpty()) {
        LazyColumn {
            items(
                items = taskList,
                key = { task ->
                    task.id
                }) { task ->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart) {
                            onSwipeToDelete(task)
                        }
                        true
                    }
                )
                val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)

                val degrees by animateFloatAsState(
                    targetValue =
                    if (dismissState.targetValue == DismissValue.Default) 0f else DELETE_ICON_MAX_ROTATION
                )

                AnimatedVisibility(
                    visible = !isDismissed,
                    enter = expandVertically(
                        animationSpec = tween(LIST_SHRINK_TWEEN_DURATION)
                    ),
                    exit = shrinkVertically(
                        animationSpec = tween(LIST_SHRINK_TWEEN_DURATION)
                    )
                ) {
                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        dismissThresholds = { FractionalThreshold(fraction = Constants.SWIPE_DISMISS_THRESHOLD) },
                        background = {
                            SwipeRedBackground(
                                degrees = degrees,
                            )
                        },
                        dismissContent = {
                            TaskItem(
                                toDoTask = task,
                                navigateToTaskScreen = navigateToTaskScreen
                            )
                        }
                    )
                }
            }
        }
    } else {
        EmptyContent()
    }
}

@Composable
fun TaskItem(
    toDoTask: ToDoTask,
    navigateToTaskScreen: (Int) -> Unit
) {
    val circleColor: Color = toDoTask.priority.color
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickableSingle {
                navigateToTaskScreen(toDoTask.id)
            },
        color = MaterialTheme.colors.primary,
        shape = RectangleShape,
        elevation = TASK_ITEM_ELEVATION,

        ) {
        Column(
            modifier = Modifier
                .padding(all = MEDIUM_PADDING)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(0.9f),
                    text = toDoTask.title,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.1f)
                        .align(Alignment.Bottom),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)) {
                        drawCircle(color = circleColor)
                    }
                }
            }

            Text(
                modifier = Modifier.fillMaxWidth(0.9f),
                text = toDoTask.description,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

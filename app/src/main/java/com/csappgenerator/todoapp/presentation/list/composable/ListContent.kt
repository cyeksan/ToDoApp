package com.csappgenerator.todoapp.presentation.list.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.csappgenerator.todoapp.domain.model.Priority
import com.csappgenerator.todoapp.domain.model.ToDoTask
import com.csappgenerator.todoapp.ui.theme.LARGE_PADDING
import com.csappgenerator.todoapp.ui.theme.PRIORITY_INDICATOR_SIZE
import com.csappgenerator.todoapp.ui.theme.TASK_ITEM_ELEVATION

@Composable
fun ListContent(taskList: List<ToDoTask>, navigateToTaskScreen: (Int) -> Unit) {
    LazyColumn {
        items(
            items = taskList,
            key = { task ->
                task.id
            }) { task ->
            TaskItem(
                toDoTask = task,
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskItem(
    toDoTask: ToDoTask,
    navigateToTaskScreen: (Int) -> Unit
) {
    val circleColor: Color = toDoTask.priority.color
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.primary,
        shape = RectangleShape,
        elevation = TASK_ITEM_ELEVATION,
        onClick = {
            navigateToTaskScreen(toDoTask.id)
        }

    ) {
        Column(
            modifier = Modifier
                .padding(all = LARGE_PADDING)
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
                modifier = Modifier.fillMaxWidth(),
                text = toDoTask.description,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )


        }
    }
   /* Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LARGE_PADDING)
            .height(SPACER_HEIGHT)
            .background(color = MaterialTheme.colors.surface)
    )*/
}

@Composable
@Preview
fun TaskItemPreview() {
    TaskItem(
        toDoTask = ToDoTask(0, "Title", "Some random text", Priority.HIGH),
        navigateToTaskScreen = {})
}

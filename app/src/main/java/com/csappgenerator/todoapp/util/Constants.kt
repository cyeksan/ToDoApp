package com.csappgenerator.todoapp.util

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.ui.unit.IntOffset

object Constants {
    const val DATABASE_TABLE = "todo_table"
    const val DATABASE_NAME = "todo_database"
    const val TASK_ARGUMENT = "taskId"
    const val MAX_TITLE_LENGTH = 30
    const val TASK_ID_DEFAULT = -1
    const val TITLE_NOT_EMPTY_ERROR = "The title of the task cannot be empty."
    const val DESCRIPTION_NOT_EMPTY_ERROR = "The description of the task cannot be empty."
    const val TWEEN_DURATION = 1000

}
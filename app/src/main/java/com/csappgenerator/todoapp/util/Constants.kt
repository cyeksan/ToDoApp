package com.csappgenerator.todoapp.util

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.ui.unit.IntOffset

object Constants {
    const val DATABASE_TABLE = "todo_table"
    const val DATABASE_NAME = "todo_database"
    const val TASK_ARGUMENT = "taskId"
    const val TITLE_NOT_EMPTY_ERROR = "The title of the task cannot be empty."
    const val DESCRIPTION_NOT_EMPTY_ERROR = "The description of the task cannot be empty."
    const val DEFAULT_ERROR = "Couldn't save the task"
    const val PREFERENCE_NAME = "todo_preferences"
    const val PREFERENCE_KEY = "sort_state"

    const val SPLASH_EXIT_ANIMATION_TWEEN_DURATION = 600
    const val TASK_ENTER_ANIMATION_TWEEN_DURATION = 600
    const val LIST_SHRINK_TWEEN_DURATION = 200
    const val LIST_SHRINK_TWEEN_DELAY = 200L
    const val SPLASH_SCREEN_DELAY = 2500L
    const val SWIPE_DISMISS_THRESHOLD = 0.3f
    const val DELETE_ICON_MAX_ROTATION = -45f
    const val MAX_TASK_TITLE_LENGTH = 30
    const val TASK_ID_DEFAULT = -1
}
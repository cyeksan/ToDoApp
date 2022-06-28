package com.csappgenerator.todoapp.presentation.common

import com.csappgenerator.todoapp.domain.model.ToDoTask
import com.csappgenerator.todoapp.presentation.task.event.TaskEvent

sealed class SnackBarState {
    object Idle: SnackBarState()
    data class Show(
        val eventType: TaskEvent,
        val task: ToDoTask?
    ): SnackBarState()
}
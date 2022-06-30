package com.csappgenerator.todoapp.presentation.task.state

import com.csappgenerator.todoapp.domain.model.ToDoTask

sealed class TaskState {
    object New : TaskState()
    data class Existing(val existingTask: ToDoTask?) : TaskState()
}
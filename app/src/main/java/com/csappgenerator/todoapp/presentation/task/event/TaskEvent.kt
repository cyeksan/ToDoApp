package com.csappgenerator.todoapp.presentation.task.event

import androidx.navigation.NavController
import com.csappgenerator.todoapp.domain.model.ToDoTask

sealed class TaskEvent {
    object Add : TaskEvent()
    object Update : TaskEvent()
    object Delete : TaskEvent()
    object DeleteAll : TaskEvent()
    data class NoAction(val navController: NavController) : TaskEvent()
}

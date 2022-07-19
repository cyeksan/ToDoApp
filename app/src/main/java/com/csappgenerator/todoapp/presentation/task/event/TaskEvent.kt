package com.csappgenerator.todoapp.presentation.task.event

import androidx.navigation.NavController

sealed class TaskEvent {
    object Add : TaskEvent()
    object Update : TaskEvent()
    object Delete : TaskEvent()
    object DeleteAll : TaskEvent()
    object Restore : TaskEvent()
    data class ShowErrorSnackBar(val message: String) : TaskEvent()
    object SaveTask : TaskEvent()
    object ExitTask : TaskEvent()
    data class NoAction(val navController: NavController) : TaskEvent()
}

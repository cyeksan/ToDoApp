package com.csappgenerator.todoapp.util

import androidx.navigation.NavController

fun NavController.navigateToSpecificTask(taskId: Int) {
    this.navigate(
        Screen.TaskScreen.route +
                "?taskId=${taskId}"
    )
}

fun NavController.navigateToNewTask() {
    this.navigate(
        Screen.TaskScreen.route
    )
}
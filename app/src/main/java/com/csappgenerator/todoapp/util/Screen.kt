package com.csappgenerator.todoapp.util

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object TaskScreen : Screen("task_screen")
    object ListScreen : Screen("list_screen")
}

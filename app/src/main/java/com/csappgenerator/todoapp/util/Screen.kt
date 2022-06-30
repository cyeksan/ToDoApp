package com.csappgenerator.todoapp.util

import com.csappgenerator.todoapp.util.Constants.LIST_SCREEN
import com.csappgenerator.todoapp.util.Constants.SPLASH_SCREEN
import com.csappgenerator.todoapp.util.Constants.TASK_SCREEN

sealed class Screen(val route: String) {
    object SplashScreen : Screen(SPLASH_SCREEN)
    object ListScreen : Screen(LIST_SCREEN)
    object TaskScreen : Screen(TASK_SCREEN)
}

package com.csappgenerator.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.csappgenerator.todoapp.presentation.list.ListScreen
import com.csappgenerator.todoapp.presentation.splash.SplashScreen
import com.csappgenerator.todoapp.presentation.task.TaskScreen
import com.csappgenerator.todoapp.ui.theme.ToDoAppTheme
import com.csappgenerator.todoapp.util.Constants.SPLASH_EXIT_ANIMATION_TWEEN_DURATION
import com.csappgenerator.todoapp.util.Constants.TASK_ARGUMENT
import com.csappgenerator.todoapp.util.Constants.TASK_ENTER_ANIMATION_TWEEN_DURATION
import com.csappgenerator.todoapp.util.Constants.TASK_ID_DEFAULT
import com.csappgenerator.todoapp.util.Screen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberAnimatedNavController()
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Screen.SplashScreen.route
                    ) {
                        composable(
                            route = Screen.SplashScreen.route,
                            exitTransition = {
                                slideOutVertically(
                                    targetOffsetY = { fullHeight -> -fullHeight },
                                    animationSpec = tween(SPLASH_EXIT_ANIMATION_TWEEN_DURATION)
                                )
                            }
                        ) {
                            SplashScreen(navController = navController)
                        }
                        composable(
                            route = Screen.ListScreen.route
                        ) {
                            ListScreen(navController = navController)
                        }
                        composable(
                            route = Screen.TaskScreen.route
                                    + "?$TASK_ARGUMENT={$TASK_ARGUMENT}",
                            arguments = listOf(
                                navArgument(
                                    name = TASK_ARGUMENT
                                ) {
                                    type = NavType.IntType
                                    defaultValue = TASK_ID_DEFAULT
                                },
                            ),
                            enterTransition = {
                                slideInHorizontally(
                                    initialOffsetX = { fullWidth -> -fullWidth },
                                    animationSpec = tween(durationMillis = TASK_ENTER_ANIMATION_TWEEN_DURATION)
                                )
                            }
                        ) {

                            TaskScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
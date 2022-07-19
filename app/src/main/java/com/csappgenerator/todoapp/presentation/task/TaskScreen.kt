package com.csappgenerator.todoapp.presentation.task

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.csappgenerator.todoapp.R
import com.csappgenerator.todoapp.presentation.task.composable.ExistingTaskAppBar
import com.csappgenerator.todoapp.presentation.task.composable.NewTaskAppBar
import com.csappgenerator.todoapp.presentation.task.composable.TaskContent
import com.csappgenerator.todoapp.presentation.task.event.TaskEvent
import com.csappgenerator.todoapp.presentation.task.state.TaskState
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    navController: NavController,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val taskState = viewModel.taskState.value
    val taskPropertyState = viewModel.taskPropertyState.value
    var taskTitle by taskPropertyState.title
    var taskDescription by taskPropertyState.description
    var taskPriority by taskPropertyState.priority
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is TaskEvent.ShowErrorSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = context.getString(R.string.snack_bar_ok_action_label),
                    )
                }
                else -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        content = {
            TaskContent(
                title = taskTitle,
                onTitleChange = {
                    taskTitle = it
                },
                description = taskDescription,
                onDescriptionChange = {
                    taskDescription = it
                },
                priority = taskPriority,
                onPrioritySelected = {
                    taskPriority = it
                }
            )
        },
        topBar = {
            when (taskState) {
                is TaskState.Existing -> {
                    ExistingTaskAppBar(
                        selectedTaskTitle = taskState.existingTask!!.title,
                        navigateBackToListScreen = {
                            viewModel.onEvent(TaskEvent.NoAction(navController))
                        },
                        onDeleteConfirmed = {
                            viewModel.onEvent(TaskEvent.Delete)
                        },
                        onUpdateClicked = {
                            viewModel.onEvent(TaskEvent.Update)
                        }
                    )
                }
                else -> {
                    NewTaskAppBar(
                        navigateBackToListScreen = {
                            viewModel.onEvent(TaskEvent.NoAction(navController))
                        },
                        addTask = {
                            viewModel.onEvent(TaskEvent.Add)
                        })
                }
            }
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                // Custom Snack bar with the custom colors
                Snackbar(
                    actionColor = MaterialTheme.colors.secondary,
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    snackbarData = data
                )
            }
        },
    )
}
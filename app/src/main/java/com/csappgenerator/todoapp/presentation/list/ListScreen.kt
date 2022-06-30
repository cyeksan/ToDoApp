package com.csappgenerator.todoapp.presentation.list

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.csappgenerator.todoapp.R
import com.csappgenerator.todoapp.presentation.common.SnackBarState
import com.csappgenerator.todoapp.presentation.list.composable.*
import com.csappgenerator.todoapp.presentation.list.event.ListEvent
import com.csappgenerator.todoapp.presentation.list.event.SearchBarEvent
import com.csappgenerator.todoapp.presentation.list.state.RequestState
import com.csappgenerator.todoapp.presentation.list.state.SearchBarState
import com.csappgenerator.todoapp.presentation.task.event.TaskEvent
import com.csappgenerator.todoapp.util.getSnackBarAction
import com.csappgenerator.todoapp.util.getSnackBarMessage
import com.csappgenerator.todoapp.util.navigateToNewTask
import com.csappgenerator.todoapp.util.navigateToSpecificTask
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListScreen(
    navController: NavController,
    viewModel: ListViewModel = hiltViewModel()
) {

    val requestState = viewModel.requestState.value
    val searchState by viewModel.searchBarState
    val searchBarTextState by viewModel.searchBarState.value.searchBarText
    val snackBarState = viewModel.snackBarState.value
    val taskEventState by viewModel.taskEventState

    val taskList = viewModel.allTasks.value

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val openDeleteAllConfirmDialog = remember { mutableStateOf(false) }

    when (viewModel.searchBarState.value) {
        is SearchBarState.SearchBarClosed -> {
            viewModel.onSearchBarEvent(SearchBarEvent.CloseSearchBar)
        }
        is SearchBarState.SearchBarOpened -> {
            viewModel.onSearchBarEvent(SearchBarEvent.OpenSearchBar)
        }
    }

    LaunchedEffect(key1 = snackBarState) {
        when (snackBarState) {
            is SnackBarState.Show -> {
                viewModel.onEvent(ListEvent.SetSnackBarEvent(snackBarState.eventType))
                scope.launch {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = taskEventState!!.getSnackBarMessage(context, snackBarState),
                        actionLabel = taskEventState!!.getSnackBarAction(context),
                    )
                    if (result == SnackbarResult.ActionPerformed &&
                        snackBarState.eventType == TaskEvent.Delete
                    ) {

                        viewModel.onEvent(ListEvent.RestoreTask(snackBarState.task!!))
                    }
                }
            }
            is SnackBarState.Idle -> {}
        }
    }

    Scaffold(
        content = {
            when (requestState) {
                is RequestState.Loading -> {
                    CircularProgressBar()
                }
                is RequestState.Success -> {
                    ListContent(
                        taskList = requestState.data,
                        onSwipeToDelete = { task ->
                            viewModel.onEvent(ListEvent.Delete(task))
                            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                        },
                        navigateToTaskScreen = { taskId ->
                            navController.navigateToSpecificTask(taskId)
                        }
                    )
                }
                else -> {}
            }
        },
        topBar = {
            when (searchState) {
                is SearchBarState.SearchBarClosed -> {
                    DefaultListAppBar(
                        onSearchClicked = {
                            viewModel.onSearchBarEvent(SearchBarEvent.OpenSearchBar)
                        },
                        onSortClicked = { priority ->
                            viewModel.onEvent(ListEvent.PersistSortingStateAndReload(priority))
                        },
                        onDeleteAllClicked = {
                            if (taskList.isEmpty()) {
                                openDeleteAllConfirmDialog.value = false
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = context.getString(R.string.no_tasks_found),
                                        actionLabel = context.getString(R.string.snack_bar_ok_action_label)
                                    )
                                }
                            } else {
                                openDeleteAllConfirmDialog.value = true
                            }
                        },
                        onDeleteAllConfirmed = {
                            viewModel.onEvent(ListEvent.DeleteAll)
                        },
                        openDeleteAllConfirmDialog = openDeleteAllConfirmDialog
                    )
                }
                else -> {
                    SearchAppBar(
                        text = searchBarTextState,
                        onTextChanged = { changedText ->
                            viewModel.onSearchBarEvent(
                                SearchBarEvent.SearchBarOnValueChanged(
                                    changedText
                                )
                            )
                        },
                        onCloseClicked = {
                            viewModel.onSearchBarEvent(SearchBarEvent.CloseSearchBar)
                        })
                }
            }
        },
        floatingActionButton = {
            ListFab(onFabClicked = {
                navController.navigateToNewTask()
            })
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
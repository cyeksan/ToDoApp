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
import com.csappgenerator.todoapp.util.navigateToNewTask
import com.csappgenerator.todoapp.util.navigateToSpecificTask
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ListScreen(
    navController: NavController,
    viewModel: ListViewModel = hiltViewModel(),

    ) {
    val requestState = viewModel.requestState.value
    val searchState by viewModel.searchBarState
    val searchBarTextState by viewModel.searchBarState.value.searchBarText
    val prepareSnackBarState = viewModel.prepareSnackBar.value
    val taskEventState by viewModel.taskEventState

    val taskList by viewModel.allTasks

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    val openConfirmDialog = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = prepareSnackBarState) {
        when (prepareSnackBarState) {
            is SnackBarState.Show -> {
                viewModel.onEvent(
                    ListEvent.ShowSnackBar(
                        prepareSnackBarState.eventType,
                        prepareSnackBarState.task
                    )
                )
                scope.launch {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message =
                        when (taskEventState) {
                            is TaskEvent.Update -> context.getString(R.string.task_updated) + " ${prepareSnackBarState.task!!.title}"
                            is TaskEvent.Delete -> context.getString(R.string.task_deleted) + " ${prepareSnackBarState.task!!.title}"
                            is TaskEvent.DeleteAll -> context.getString(R.string.all_tasks_deleted)
                            else -> ""
                        },
                        actionLabel =
                        when (taskEventState) {
                            is TaskEvent.Update -> context.getString(R.string.snack_bar_ok_action_label)
                            is TaskEvent.Delete -> context.getString(R.string.snack_bar_undo_action_label)
                            is TaskEvent.DeleteAll -> context.getString(R.string.snack_bar_ok_action_label)
                            else -> ""
                        },
                    )
                    if (result == SnackbarResult.ActionPerformed)
                        prepareSnackBarState.task?.let { ListEvent.RestoreTask(it) }
                            ?.let { viewModel.onEvent(it) }
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
                        },
                        navigateToTaskScreen = { taskId ->
                            navController.navigateToSpecificTask(taskId)
                        })
                }
                else -> {
                    EmptyContent()
                }
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
                                openConfirmDialog.value = false
                                scope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = context.getString(R.string.no_tasks_found),
                                        actionLabel = context.getString(R.string.snack_bar_ok_action_label)
                                    )
                                }
                            } else {
                                openConfirmDialog.value = true
                            }

                        },
                        onDeleteAllConfirmed = {
                            viewModel.onEvent(ListEvent.DeleteAll)
                        },
                        openDialog = openConfirmDialog
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

    when (viewModel.prepareSearchBar.value) {
        is SearchBarState.SearchBarClosed -> {
            viewModel.onSearchBarEvent(SearchBarEvent.CloseSearchBar)
        }
        else -> {}
    }
}
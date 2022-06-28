package com.csappgenerator.todoapp.presentation.list

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.csappgenerator.todoapp.R
import com.csappgenerator.todoapp.presentation.common.SnackBarState
import com.csappgenerator.todoapp.presentation.list.composable.*
import com.csappgenerator.todoapp.presentation.list.event.ListEvent
import com.csappgenerator.todoapp.presentation.list.event.SearchBarEvent
import com.csappgenerator.todoapp.presentation.list.state.ListState
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
    val listState = viewModel.listState.value

    val searchState by viewModel.searchBarState
    val searchBarTextState by viewModel.searchBarState.value.searchBarText
    val prepareSnackBarState = viewModel.prepareSnackBar.value
    val updateOrDeleteState by viewModel.taskEventState

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    when (viewModel.prepareSearchBar.value) {
        is SearchBarState.SearchBarClosed -> {
            viewModel.onSearchBarEvent(SearchBarEvent.CloseSearchBar)
        }
        else -> {}
    }

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
                        when (updateOrDeleteState) {
                            is TaskEvent.Update -> context.getString(R.string.note_updated) + " ${prepareSnackBarState.task!!.title}"
                            is TaskEvent.Delete -> context.getString(R.string.note_deleted) + " ${prepareSnackBarState.task!!.title}"
                            is TaskEvent.DeleteAll -> context.getString(R.string.all_notes_deleted)
                            else -> ""
                        },
                        actionLabel =
                        when (updateOrDeleteState) {
                            is TaskEvent.Update -> context.getString(R.string.snack_bar_ok_action_label)
                            is TaskEvent.Delete -> context.getString(R.string.snack_bar_undo_action_label)
                            else -> ""
                        },
                    )
                    if (result == SnackbarResult.ActionPerformed)
                        viewModel.onEvent(ListEvent.RestoreTask(prepareSnackBarState.task!!))
                }
            }
            is SnackBarState.Idle -> {}
        }
    }

    Scaffold(
        content = {
            when (listState) {
                is ListState.Loading -> {
                    CircularProgressBar()
                }
                is ListState.EmptyList -> {
                    EmptyContent()
                }
                is ListState.Success -> {
                    ListContent(
                        taskList = listState.data,
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

                        },
                        onDeleteAllClicked = {
                            viewModel.onEvent(ListEvent.DeleteAll)
                        }
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
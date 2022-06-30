package com.csappgenerator.todoapp.presentation.list.composable

import android.content.Context
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import com.csappgenerator.todoapp.R
import com.csappgenerator.todoapp.presentation.common.SnackBarState
import com.csappgenerator.todoapp.presentation.list.ListViewModel
import com.csappgenerator.todoapp.presentation.list.event.ListEvent
import com.csappgenerator.todoapp.presentation.task.event.TaskEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun EventSnackBars(
    context: Context,
    viewModel: ListViewModel,
    prepareSnackBarState: SnackBarState,
    taskEventState: TaskEvent?,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,

) {
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

}
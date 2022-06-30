package com.csappgenerator.todoapp.util

import android.content.Context
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import com.csappgenerator.todoapp.R
import com.csappgenerator.todoapp.presentation.common.SnackBarState
import com.csappgenerator.todoapp.presentation.task.event.TaskEvent
import com.csappgenerator.todoapp.util.Constants.PREFERENCE_NAME

fun NavController.navigateToSpecificTask(taskId: Int) {
    this.navigate(
        Screen.TaskScreen.route +
                "?${Constants.TASK_ARGUMENT}=${taskId}"
    )
}

fun NavController.navigateToNewTask() {
    this.navigate(
        Screen.TaskScreen.route
    )
}

fun NavController.navigateFromSplashToList() {
    this.navigate(
        Screen.ListScreen.route
    )
}

fun TaskEvent.getSnackBarMessage(context: Context, snackBarState: SnackBarState.Show): String {
    return when (this) {
        is TaskEvent.Update -> context.getString(R.string.task_updated) + " ${snackBarState.task!!.title}"
        is TaskEvent.Delete -> context.getString(R.string.task_deleted) + " ${snackBarState.task!!.title}"
        is TaskEvent.DeleteAll -> context.getString(R.string.all_tasks_deleted)
        is TaskEvent.Restore -> context.getString(R.string.task_restored) + " ${snackBarState.task!!.title}"
        else -> ""
    }
}

fun TaskEvent.getSnackBarAction(context: Context): String {
    return when (this) {
        is TaskEvent.Delete -> context.getString(R.string.snack_bar_undo_action_label)
        else -> context.getString(R.string.snack_bar_ok_action_label)
    }
}

fun Modifier.clickableSingle(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }
    Modifier.clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        onClick = { multipleEventsCutter.processEvent { onClick() } },
        role = role,
        indication = LocalIndication.current,
        interactionSource = remember { MutableInteractionSource() }
    )
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)


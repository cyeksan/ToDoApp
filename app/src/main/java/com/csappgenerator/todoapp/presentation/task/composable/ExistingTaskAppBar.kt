package com.csappgenerator.todoapp.presentation.task.composable

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.csappgenerator.todoapp.R
import com.csappgenerator.todoapp.presentation.common.composable.DisplayAlertDialog

@Composable
fun ExistingTaskAppBar(
    selectedTaskTitle: String,
    navigateBackToListScreen: () -> Unit,
    onDeleteConfirmed: () -> Unit,
    onUpdateClicked: () -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }

    TopAppBar(
        navigationIcon = {
            CloseAction(onCloseClicked = navigateBackToListScreen)
        },
        title = {
            Text(text = selectedTaskTitle)
        },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            DeleteAction(
                onDeleteConfirmed = onDeleteConfirmed, openDeleteItemConfirmDialog = openDialog,
                selectedTaskTitle = selectedTaskTitle
            )
            UpdateAction(
                onUpdateClicked = onUpdateClicked
            )
        }
    )

}

@Composable
fun CloseAction(
    onCloseClicked: () -> Unit
) {
    IconButton(onClick = onCloseClicked) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(R.string.close_icon)
        )
    }
}

@Composable
fun DeleteAction(
    onDeleteConfirmed: () -> Unit,
    openDeleteItemConfirmDialog: MutableState<Boolean>,
    selectedTaskTitle: String
) {
    DisplayAlertDialog(
        title = stringResource(id = R.string.delete_confirm_title, selectedTaskTitle),
        message = stringResource(id = R.string.delete_confirm_message, selectedTaskTitle),
        openDeleteItemConfirmDialog = openDeleteItemConfirmDialog,
        closeDialog = { openDeleteItemConfirmDialog.value = false },
        onYesClicked = onDeleteConfirmed
    )

    IconButton(onClick = {
        openDeleteItemConfirmDialog.value = true
    }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(R.string.delete_icon)
        )
    }
}

@Composable
fun UpdateAction(
    onUpdateClicked: () -> Unit
) {
    IconButton(onClick = onUpdateClicked) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(R.string.update_icon)
        )
    }
}
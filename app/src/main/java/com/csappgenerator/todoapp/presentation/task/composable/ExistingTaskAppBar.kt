package com.csappgenerator.todoapp.presentation.task.composable

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.csappgenerator.todoapp.R

@Composable
fun ExistingTaskAppBar(
    selectedTaskTitle: String,
    navigateBackToListScreen: () -> Unit,
    onDeleteClicked: () -> Unit,
    onUpdateClicked: () -> Unit
) {
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
                onDeleteClicked = onDeleteClicked
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
    onDeleteClicked: () -> Unit
) {
    IconButton(onClick = onDeleteClicked) {
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
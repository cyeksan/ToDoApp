package com.csappgenerator.todoapp.presentation.list.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.csappgenerator.todoapp.R
import com.csappgenerator.todoapp.util.Priority
import com.csappgenerator.todoapp.presentation.common.composable.PriorityItem
import com.csappgenerator.todoapp.presentation.task.composable.DisplayAlertDialog
import com.csappgenerator.todoapp.ui.theme.MEDIUM_PADDING


@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllClicked: () -> Unit,
    onDeleteAllConfirmed: () -> Unit,
    openDialog: MutableState<Boolean>
) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.list_screen_title))
        },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            ListAppBarActions(
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteAllConfirmed = onDeleteAllConfirmed,
                onDeleteAllClicked = onDeleteAllClicked,
                openDialog = openDialog
            )
        }
    )
}

@Composable
fun ListAppBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteAllClicked: () -> Unit,
    onDeleteAllConfirmed: () -> Unit,
    openDialog: MutableState<Boolean>
) {

    SearchAction(onSearchClicked = onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAllAction(onDeleteAllClicked = onDeleteAllClicked,
        onDeleteAllConfirmed = onDeleteAllConfirmed,
        openDialog = openDialog)
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit
) {
    IconButton(onClick = { onSearchClicked() }) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(R.string.search_icon)
        )
    }
}

@Composable
fun SortAction(
    onSortClicked: (Priority) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = {
        expanded = true
    }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filter_list),
            contentDescription = stringResource(R.string.filter_button)
        )
    }

    DropdownMenu(
        modifier = Modifier.background(
            color = MaterialTheme.colors.primaryVariant
        ),
        expanded = expanded,
        onDismissRequest = {
            expanded = false
        }) {
        DropdownMenuItem(onClick = {
            expanded = false
            onSortClicked(Priority.LOW)
        }) {
            PriorityItem(priority = Priority.LOW)
        }
        DropdownMenuItem(onClick = {
            expanded = false
            onSortClicked(Priority.HIGH)
        }) {
            PriorityItem(priority = Priority.HIGH)
        }
        DropdownMenuItem(onClick = {
            expanded = false
            onSortClicked(Priority.NONE)
        }) {
            PriorityItem(priority = Priority.NONE)
        }
    }
}

@Composable
fun DeleteAllAction(
    onDeleteAllClicked: () -> Unit,
    onDeleteAllConfirmed: () -> Unit,
    openDialog: MutableState<Boolean>,
) {
    var expanded by remember { mutableStateOf(false) }

    DisplayAlertDialog(
        title = stringResource(id = R.string.delete_all_confirm_title),
        message = stringResource(id = R.string.delete_all_confirm_message),
        openDialog = openDialog,
        closeDialog = { openDialog.value = false },
        onYesClicked = onDeleteAllConfirmed
    )
    IconButton(onClick = {
        expanded = true
    }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_vertical_menu),
            contentDescription = stringResource(R.string.delete_button)
        )
    }

    DropdownMenu(
        modifier = Modifier.background(
            color = MaterialTheme.colors.primaryVariant
        ),
        expanded = expanded,
        onDismissRequest = {
            expanded = false
        }) {
        DropdownMenuItem(onClick = {
            onDeleteAllClicked()
            expanded = false
        }) {
            Text(
                modifier = Modifier.padding(start = MEDIUM_PADDING),
                text = stringResource(R.string.delete_all),
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}
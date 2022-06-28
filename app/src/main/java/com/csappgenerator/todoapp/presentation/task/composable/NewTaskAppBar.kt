package com.csappgenerator.todoapp.presentation.task.composable

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.csappgenerator.todoapp.R


@Composable
fun NewTaskAppBar(
    navigateBackToListScreen: () -> Unit,
    addTask: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = navigateBackToListScreen)
        },
        title = {
            Text(text = stringResource(R.string.add_task))
        },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            AddAction(onAddClicked = addTask)
        }
    )

}

@Composable
fun BackAction(
    onBackClicked: () -> Unit
) {
    IconButton(onClick = onBackClicked) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back_arrow)
        )
    }
}

@Composable
fun AddAction(
    onAddClicked: () -> Unit
) {
    IconButton(onClick = onAddClicked) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(R.string.add_icon)
        )
    }
}

@Composable
@Preview
fun NewTaskAppBarPreview() {
    NewTaskAppBar(navigateBackToListScreen = { }, addTask = {})
}
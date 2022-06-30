package com.csappgenerator.todoapp.presentation.list.composable

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.csappgenerator.todoapp.R

@Composable
fun ListFab(onFabClicked: () -> Unit) {
    FloatingActionButton(
        onClick = {
            onFabClicked()
        }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_button),
        )
    }
}
package com.csappgenerator.todoapp.presentation.task.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.csappgenerator.todoapp.R
import com.csappgenerator.todoapp.ui.theme.MEDIUM_PADDING
import com.csappgenerator.todoapp.ui.theme.SMALL_PADDING
import com.csappgenerator.todoapp.util.Constants.MAX_TASK_TITLE_LENGTH
import com.csappgenerator.todoapp.util.Priority

@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(all = MEDIUM_PADDING)
            .fillMaxSize()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { if (it.length <= MAX_TASK_TITLE_LENGTH) onTitleChange(it) },
            label = {
                Text(text = stringResource(R.string.title_label))
            },
            textStyle = MaterialTheme.typography.body1,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = MaterialTheme.colors.secondary,
                focusedIndicatorColor = MaterialTheme.colors.secondary,
                cursorColor = MaterialTheme.colors.secondary,
                backgroundColor = MaterialTheme.colors.background,
            )
        )

        Divider(
            modifier = Modifier
                .height(SMALL_PADDING),
            color = MaterialTheme.colors.background
        )

        PriorityDropdown(
            priority = priority,
            onPrioritySelected = onPrioritySelected
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = { onDescriptionChange(it) },
            label = {
                Text(text = stringResource(R.string.description_label))
            },
            textStyle = MaterialTheme.typography.body1,
            colors = TextFieldDefaults.textFieldColors(
                focusedLabelColor = MaterialTheme.colors.secondary,
                focusedIndicatorColor = MaterialTheme.colors.secondary,
                cursorColor = MaterialTheme.colors.secondary,
                backgroundColor = MaterialTheme.colors.background,
            )
        )
    }
}
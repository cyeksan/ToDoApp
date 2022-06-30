package com.csappgenerator.todoapp.presentation.list.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.csappgenerator.todoapp.ui.theme.CIRCULAR_PROGRESS_BAR_SIZE

@Composable
fun CircularProgressBar() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(CIRCULAR_PROGRESS_BAR_SIZE),
            color = MaterialTheme.colors.secondary
        )
    }
}
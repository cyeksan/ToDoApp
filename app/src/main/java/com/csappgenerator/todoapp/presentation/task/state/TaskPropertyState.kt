package com.csappgenerator.todoapp.presentation.task.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.csappgenerator.todoapp.util.Priority

data class TaskPropertyState(
    val id: Int? = null,
    val title: MutableState<String> = mutableStateOf("") ,
    val description: MutableState<String> = mutableStateOf(""),
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)
)
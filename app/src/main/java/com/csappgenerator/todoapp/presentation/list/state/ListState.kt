package com.csappgenerator.todoapp.presentation.list.state

import com.csappgenerator.todoapp.domain.model.ToDoTask

sealed class ListState {
    object Loading: ListState()
    data class Success(val data: List<ToDoTask>): ListState()
    object EmptyList: ListState()
    data class Error(val error: Throwable): ListState()
}
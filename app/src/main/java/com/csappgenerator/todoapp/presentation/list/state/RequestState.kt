package com.csappgenerator.todoapp.presentation.list.state

import com.csappgenerator.todoapp.domain.model.ToDoTask

sealed class RequestState {
    object Loading : RequestState()
    data class Success(val data: List<ToDoTask>) : RequestState()
}
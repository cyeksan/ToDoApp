package com.csappgenerator.todoapp.presentation.common

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.csappgenerator.todoapp.domain.model.ToDoTask
import com.csappgenerator.todoapp.presentation.list.state.SearchBarState
import com.csappgenerator.todoapp.presentation.task.event.TaskEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class SharedViewModel @Inject constructor(
    val prepareSnackBar: MutableState<SnackBarState>,
    val prepareSearchBar: MutableState<SearchBarState>
): ViewModel() {

    fun MutableState<SnackBarState>.setSnackBarStateToIdle() {
        this.value = SnackBarState.Idle
    }

    fun MutableState<SnackBarState>.setSnackBarStateToShow(eventType: TaskEvent, task: ToDoTask?) {
        this.value = SnackBarState.Show(eventType, task)
    }

    fun MutableState<SearchBarState>.closeSearchBar() {
        this.value = SearchBarState.SearchBarClosed
    }

    fun MutableState<SearchBarState>.openSearchBar() {
        this.value = SearchBarState.SearchBarOpened
    }

}
package com.csappgenerator.todoapp.presentation.common

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.csappgenerator.todoapp.domain.model.ToDoTask
import com.csappgenerator.todoapp.presentation.list.state.SearchBarState
import com.csappgenerator.todoapp.presentation.task.event.TaskEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class SharedViewModel @Inject constructor(
    val snackBarState: MutableState<SnackBarState>,
    val searchBarState: MutableState<SearchBarState>
) : ViewModel() {

    init {
        snackBarState.hide()
        searchBarState.close()
    }

    private fun MutableState<SnackBarState>.hide() {
        this.value = SnackBarState.Idle
    }

    fun MutableState<SearchBarState>.close() {
        searchBarState.value.searchBarText.value = ""
        this.value = SearchBarState.SearchBarClosed
    }

    fun MutableState<SnackBarState>.show(eventType: TaskEvent, task: ToDoTask?) {
        this.value = SnackBarState.Show(eventType, task)
    }

    fun MutableState<SearchBarState>.open() {
        this.value = SearchBarState.SearchBarOpened
    }

}
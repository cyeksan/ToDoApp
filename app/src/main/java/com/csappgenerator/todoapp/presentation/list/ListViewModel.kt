package com.csappgenerator.todoapp.presentation.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.csappgenerator.todoapp.domain.model.ToDoTask
import com.csappgenerator.todoapp.domain.use_case.wrapper.ListUseCases
import com.csappgenerator.todoapp.presentation.common.SharedViewModel
import com.csappgenerator.todoapp.presentation.common.SnackBarState
import com.csappgenerator.todoapp.presentation.list.event.ListEvent
import com.csappgenerator.todoapp.presentation.list.event.SearchBarEvent
import com.csappgenerator.todoapp.presentation.list.state.ListState
import com.csappgenerator.todoapp.presentation.list.state.SearchBarState
import com.csappgenerator.todoapp.presentation.task.event.TaskEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val useCases: ListUseCases,
    snackBarState: MutableState<SnackBarState>,
    searchBarState: MutableState<SearchBarState>
) : SharedViewModel(
    prepareSnackBar = snackBarState,
    prepareSearchBar = searchBarState
) {

    private val _searchBarState =
        mutableStateOf<SearchBarState>(SearchBarState.SearchBarClosed)
    val searchBarState: State<SearchBarState> = _searchBarState

    private val _listState =
        mutableStateOf<ListState?>(null)
    val listState: State<ListState?> = _listState

    private val _taskEventState =
        mutableStateOf<TaskEvent?>(null)
    val taskEventState: State<TaskEvent?> = _taskEventState

    private val _allTasks = mutableStateOf<List<ToDoTask>?>(null)
    private val allTasks: State<List<ToDoTask>?> = _allTasks

    init {
        getAllTasks()
    }

    private fun getAllTasks() {
        _listState.value = ListState.Loading
        try {
            viewModelScope.launch {
                useCases.getAllTasks().collect { toDoTaskList ->
                    _allTasks.value = toDoTaskList
                    _listState.value = ListState.Success(toDoTaskList)
                    if (toDoTaskList.isEmpty()) {
                        _listState.value = ListState.EmptyList
                    }
                }

            }
        } catch (ex: Exception) {
            _listState.value = ListState.Error(ex)
        }

    }

    fun onSearchBarEvent(event: SearchBarEvent) {
        when (event) {
            is SearchBarEvent.OpenSearchBar -> {
                prepareSearchBar.openSearchBar()
                _searchBarState.value = SearchBarState.SearchBarOpened
            }
            is SearchBarEvent.CloseSearchBar -> {
                _searchBarState.value.searchBarText.value = ""
                _searchBarState.value = SearchBarState.SearchBarClosed
                _listState.value = ListState.Success(allTasks.value!!)
            }

            is SearchBarEvent.SearchBarOnValueChanged -> {
                _searchBarState.value.searchBarText.value = event.value
                viewModelScope.launch {
                    _listState.value = ListState.Loading
                    try {
                        viewModelScope.launch {
                            useCases.searchDatabase("%${event.value}%").collect { toDoTaskList ->
                                _listState.value = ListState.Success(toDoTaskList)
                                if (toDoTaskList.isEmpty()) {
                                    _listState.value = ListState.EmptyList
                                }
                            }

                        }
                    } catch (ex: Exception) {
                        _listState.value = ListState.Error(ex)
                    }
                }
            }

        }
    }

    fun onEvent(event: ListEvent) {
        when (event) {
            is ListEvent.ShowSnackBar -> {
                when (event.eventType) {
                    is TaskEvent.Update -> {
                        _taskEventState.value = TaskEvent.Update
                    }
                    is TaskEvent.Delete -> {
                        _taskEventState.value = TaskEvent.Delete
                    }
                    is TaskEvent.DeleteAll -> {
                        _taskEventState.value = TaskEvent.DeleteAll
                    }
                    else -> {}
                }
            }
            is ListEvent.RestoreTask -> {
                viewModelScope.launch {
                    useCases.addTask(event.task)
                }
            }

            is ListEvent.DeleteAll -> {
                try {
                    viewModelScope.launch {
                        useCases.deleteAllTasks()
                    }
                    prepareSnackBar.setSnackBarStateToShow(TaskEvent.DeleteAll, null)

                } catch (ex: Exception) {
                    _listState.value = ListState.Error(ex)
                }
            }
        }
    }

}

package com.csappgenerator.todoapp.presentation.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.csappgenerator.todoapp.domain.model.ToDoTask
import com.csappgenerator.todoapp.domain.repository.DataStoreRepository
import com.csappgenerator.todoapp.domain.use_case.wrapper.ListUseCases
import com.csappgenerator.todoapp.presentation.common.SharedViewModel
import com.csappgenerator.todoapp.presentation.common.SnackBarState
import com.csappgenerator.todoapp.presentation.list.event.ListEvent
import com.csappgenerator.todoapp.presentation.list.event.SearchBarEvent
import com.csappgenerator.todoapp.presentation.list.state.RequestState
import com.csappgenerator.todoapp.presentation.list.state.SearchBarState
import com.csappgenerator.todoapp.presentation.task.event.TaskEvent
import com.csappgenerator.todoapp.util.Constants.LIST_SHRINK_TWEEN_DELAY
import com.csappgenerator.todoapp.util.OrderType
import com.csappgenerator.todoapp.util.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val useCases: ListUseCases,
    snackBarState: MutableState<SnackBarState>,
    searchBarState: MutableState<SearchBarState>,
    private val dataStoreRepository: DataStoreRepository
) : SharedViewModel(
    snackBarState = snackBarState,
    searchBarState = searchBarState
) {

    private val _requestState =
        mutableStateOf<RequestState?>(null)
    val requestState: State<RequestState?> = _requestState

    private val _sortState =
        mutableStateOf<OrderType>(OrderType.None)
    private val sortState: State<OrderType> = _sortState

    private val _taskEventState =
        mutableStateOf<TaskEvent?>(null)
    val taskEventState: State<TaskEvent?> = _taskEventState

    private val _allTasks = mutableStateOf<List<ToDoTask>>(emptyList())
    val allTasks: State<List<ToDoTask>> = _allTasks

    init {
        viewModelScope.launch {
            _requestState.value = RequestState.Loading
            dataStoreRepository.readSortState().map { priorityName: String ->
                Priority.valueOf(priorityName)
            }.collect { priority ->
                _sortState.value = priority.toOrderType()
                getAllTasks(sortState.value, _allTasks)
            }
        }
    }

    private fun getAllTasks(orderType: OrderType, allTasks: MutableState<List<ToDoTask>>) {
        viewModelScope.launch {
            useCases.getAllTasks(orderType).collect { toDoTaskList ->
                allTasks.value = toDoTaskList
                _requestState.value = RequestState.Success(toDoTaskList)
            }
        }
    }

    fun onSearchBarEvent(event: SearchBarEvent) {
        when (event) {
            is SearchBarEvent.OpenSearchBar -> {
                searchBarState.open()
            }
            is SearchBarEvent.CloseSearchBar -> {
                searchBarState.close()
                _requestState.value = RequestState.Success(allTasks.value)
            }
            is SearchBarEvent.SearchBarOnValueChanged -> {
                searchBarState.value.searchBarText.value = event.value
                _requestState.value = RequestState.Loading
                viewModelScope.launch {
                    useCases.searchDatabase("%${event.value}%").collect { toDoTaskList ->
                        _requestState.value = RequestState.Success(toDoTaskList)
                    }
                }
            }
        }
    }

    fun onEvent(event: ListEvent) {
        when (event) {
            is ListEvent.SetSnackBarEvent -> {
                _taskEventState.value = event.eventType
            }
            is ListEvent.Delete -> {
                viewModelScope.launch {
                    delay(LIST_SHRINK_TWEEN_DELAY)
                    useCases.deleteTask(event.task)
                    snackBarState.show(TaskEvent.Delete, event.task)
                }
            }
            is ListEvent.RestoreTask -> {
                viewModelScope.launch {
                    useCases.addTask(event.task)
                    snackBarState.show(TaskEvent.Restore, event.task)
                }
            }
            is ListEvent.DeleteAll -> {
                viewModelScope.launch {
                    useCases.deleteAllTasks()
                    snackBarState.show(TaskEvent.DeleteAll, null)
                }
            }
            is ListEvent.PersistSortingStateAndReload -> {
                viewModelScope.launch {
                    dataStoreRepository.saveSortState(event.priority)
                    _sortState.value = event.priority.toOrderType()
                }
            }
        }
    }
}

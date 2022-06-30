package com.csappgenerator.todoapp.presentation.list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.csappgenerator.todoapp.data.repository.DataStoreRepository
import com.csappgenerator.todoapp.domain.model.ToDoTask
import com.csappgenerator.todoapp.domain.use_case.wrapper.ListUseCases
import com.csappgenerator.todoapp.presentation.common.SharedViewModel
import com.csappgenerator.todoapp.presentation.common.SnackBarState
import com.csappgenerator.todoapp.presentation.list.event.ListEvent
import com.csappgenerator.todoapp.presentation.list.event.SearchBarEvent
import com.csappgenerator.todoapp.presentation.list.state.RequestState
import com.csappgenerator.todoapp.presentation.list.state.SearchBarState
import com.csappgenerator.todoapp.presentation.task.event.TaskEvent
import com.csappgenerator.todoapp.util.OrderType
import com.csappgenerator.todoapp.util.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
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
    prepareSnackBar = snackBarState,
    prepareSearchBar = searchBarState
) {

    private val _searchBarState =
        mutableStateOf<SearchBarState>(SearchBarState.SearchBarClosed)
    val searchBarState: State<SearchBarState> = _searchBarState

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
            dataStoreRepository.readSortState.map { priorityName: String ->
                Priority.valueOf(priorityName)
            }.collect { priority ->
                _sortState.value = priority.toOrderType()
                getAllTasks(sortState.value, _allTasks)

            }

        }
    }


    private fun getAllTasks(orderType: OrderType, allTasks: MutableState<List<ToDoTask>>) {
        _requestState.value = RequestState.Loading
        try {
            viewModelScope.launch {
                useCases.getAllTasks(orderType).collect { toDoTaskList ->
                    allTasks.value = toDoTaskList
                    _requestState.value = RequestState.Success(toDoTaskList)
                }

            }
        } catch (ex: Exception) {
            _requestState.value = RequestState.Error(ex)
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
                _requestState.value = RequestState.Success(allTasks.value)
            }

            is SearchBarEvent.SearchBarOnValueChanged -> {
                _searchBarState.value.searchBarText.value = event.value
                viewModelScope.launch {
                    _requestState.value = RequestState.Loading
                    try {
                        viewModelScope.launch {
                            useCases.searchDatabase("%${event.value}%").collect { toDoTaskList ->
                                _requestState.value = RequestState.Success(toDoTaskList)
                            }

                        }
                    } catch (ex: Exception) {
                        _requestState.value = RequestState.Error(ex)
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
                        prepareSnackBar.setSnackBarStateToShow(TaskEvent.DeleteAll, null)
                    }

                } catch (ex: Exception) {
                    _requestState.value = RequestState.Error(ex)
                }
            }
            is ListEvent.PersistSortingStateAndReload -> {
                viewModelScope.launch {
                    dataStoreRepository.persistSortState(event.priority)
                    _sortState.value = event.priority.toOrderType()
                    getAllTasks(sortState.value, _allTasks)
                }
            }
            is ListEvent.Delete -> {
                viewModelScope.launch {
                    useCases.deleteTask(event.task)
                    prepareSnackBar.setSnackBarStateToShow(TaskEvent.Delete, event.task)
                }
            }
        }
    }

}

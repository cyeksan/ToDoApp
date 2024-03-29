package com.csappgenerator.todoapp.presentation.task

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.csappgenerator.todoapp.domain.model.InvalidToDoTaskException
import com.csappgenerator.todoapp.domain.model.ToDoTask
import com.csappgenerator.todoapp.domain.use_case.wrapper.TaskUseCases
import com.csappgenerator.todoapp.presentation.common.SharedViewModel
import com.csappgenerator.todoapp.presentation.common.SnackBarState
import com.csappgenerator.todoapp.presentation.list.state.SearchBarState
import com.csappgenerator.todoapp.presentation.task.event.TaskEvent
import com.csappgenerator.todoapp.presentation.task.state.TaskPropertyState
import com.csappgenerator.todoapp.presentation.task.state.TaskState
import com.csappgenerator.todoapp.util.Constants.DEFAULT_ERROR
import com.csappgenerator.todoapp.util.Constants.TASK_ARGUMENT
import com.csappgenerator.todoapp.util.Constants.TASK_ID_DEFAULT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val useCases: TaskUseCases,
    savedStateHandle: SavedStateHandle,
    snackBarState: MutableState<SnackBarState>,
    searchBarState: MutableState<SearchBarState>

) : SharedViewModel(
    snackBarState = snackBarState,
    searchBarState = searchBarState
) {

    private val _taskState =
        mutableStateOf<TaskState>(TaskState.New)
    val taskState: State<TaskState> = _taskState

    private val _taskPropertyState = mutableStateOf(TaskPropertyState())
    val taskPropertyState: State<TaskPropertyState> = _taskPropertyState

    private var currentTaskId: Int? = null

    private val _eventFlow = MutableSharedFlow<TaskEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        setTaskState(savedStateHandle)
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.NoAction -> {
                viewModelScope.launch {
                    _eventFlow.emit(TaskEvent.ExitTask)
                }
            }
            is TaskEvent.Add -> {
                viewModelScope.launch {
                    try {
                        val task = ToDoTask(
                            title = taskPropertyState.value.title.value,
                            description = taskPropertyState.value.description.value,
                            priority = taskPropertyState.value.priority.value,
                        )
                        useCases.addTask(task)
                        _eventFlow.emit(TaskEvent.SaveTask)
                    } catch (ex: InvalidToDoTaskException) {
                        _eventFlow.emit(
                            TaskEvent.ShowErrorSnackBar(
                                ex.localizedMessage ?: DEFAULT_ERROR
                            )
                        )
                    }
                }
            }
            is TaskEvent.Update -> {
                viewModelScope.launch {
                    try {
                        val task = ToDoTask(
                            id = currentTaskId!!,
                            title = taskPropertyState.value.title.value,
                            description = taskPropertyState.value.description.value,
                            priority = taskPropertyState.value.priority.value
                        )
                        useCases.updateTask(task)
                        snackBarState.show(TaskEvent.Update, task)
                        _eventFlow.emit(TaskEvent.SaveTask)
                    } catch (ex: InvalidToDoTaskException) {
                        _eventFlow.emit(
                            TaskEvent.ShowErrorSnackBar(
                                ex.localizedMessage ?: DEFAULT_ERROR
                            )
                        )
                    }
                }
            }

            is TaskEvent.Delete -> {
                viewModelScope.launch {
                    val task = ToDoTask(
                        id = currentTaskId!!,
                        title = taskPropertyState.value.title.value,
                        description = taskPropertyState.value.description.value,
                        priority = taskPropertyState.value.priority.value
                    )
                    useCases.deleteTask(task)
                    snackBarState.show(TaskEvent.Delete, task)
                    _eventFlow.emit(TaskEvent.SaveTask)
                }
            }

            else -> {}
        }
    }

    private fun setTaskState(savedStateHandle: SavedStateHandle) {
        savedStateHandle.get<Int>(TASK_ARGUMENT)?.let { taskId ->
            currentTaskId = taskId
            // taskId == TASK_ID_DEFAULT --> adding a new task
            if (taskId != TASK_ID_DEFAULT) {
                viewModelScope.launch {
                    useCases.getSelectedTask(taskId).also { toDoTask ->
                        _taskState.value = TaskState.Existing(toDoTask)
                        _taskPropertyState.value = taskPropertyState.value.copy(
                            title = mutableStateOf(toDoTask.title),
                            description = mutableStateOf(toDoTask.description),
                            priority = mutableStateOf(toDoTask.priority)
                        )

                    }
                }
            }
        }
    }
}
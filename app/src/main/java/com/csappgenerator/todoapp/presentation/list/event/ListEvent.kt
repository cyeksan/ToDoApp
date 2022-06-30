package com.csappgenerator.todoapp.presentation.list.event

import com.csappgenerator.todoapp.domain.model.ToDoTask
import com.csappgenerator.todoapp.presentation.task.event.TaskEvent
import com.csappgenerator.todoapp.util.Priority

sealed class ListEvent {
    data class SetSnackBarEvent(val eventType: TaskEvent) : ListEvent()
    data class RestoreTask(val task: ToDoTask) : ListEvent()
    data class Delete(val task: ToDoTask) : ListEvent()
    object DeleteAll : ListEvent()
    data class PersistSortingStateAndReload(val priority: Priority) : ListEvent()
}
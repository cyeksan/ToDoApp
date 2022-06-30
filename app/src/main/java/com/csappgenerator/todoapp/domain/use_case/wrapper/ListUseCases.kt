package com.csappgenerator.todoapp.domain.use_case.wrapper

import com.csappgenerator.todoapp.domain.use_case.*

data class ListUseCases(
    val addTask: AddTask,
    val deleteTask: DeleteTask,
    val deleteAllTasks: DeleteAllTasks,
    val getAllTasks: GetAllTasks,
    val getSelectedTask: GetSelectedTask,
    val searchDatabase: SearchDatabase,
    val sortByAscendingPriority: SortByAscendingPriority,
    val sortByDescendingPriority: SortByDescendingPriority,
)
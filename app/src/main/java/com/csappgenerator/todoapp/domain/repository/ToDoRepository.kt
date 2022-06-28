package com.csappgenerator.todoapp.domain.repository

import com.csappgenerator.todoapp.domain.model.ToDoTask
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    fun getAllTasks(): Flow<List<ToDoTask>>
    suspend fun getSelectedTask(taskId: Int) : ToDoTask
    suspend fun addTask(toDoTask: ToDoTask)
    suspend fun updateTask(toDoTask: ToDoTask)
    suspend fun deleteTask(toDoTask: ToDoTask)
    suspend fun deleteAllTasks()
    fun searchDatabase(searchQuery: String) : Flow<List<ToDoTask>>
    fun sortByAscendingPriority(): Flow<List<ToDoTask>>
    fun sortByDescendingPriority(): Flow<List<ToDoTask>>
}
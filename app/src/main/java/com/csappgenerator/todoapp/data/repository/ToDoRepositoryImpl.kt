package com.csappgenerator.todoapp.data.repository

import com.csappgenerator.todoapp.data.local.ToDoDao
import com.csappgenerator.todoapp.domain.model.ToDoTask
import com.csappgenerator.todoapp.domain.repository.ToDoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor(
    private val toDoDao: ToDoDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ToDoRepository {

    override fun getAllTasks(): Flow<List<ToDoTask>> {
        return toDoDao.getAllTasks()
    }

    override suspend fun getSelectedTask(taskId: Int): ToDoTask = withContext(ioDispatcher) {
        return@withContext toDoDao.getSelectedTask(taskId)
    }

    override suspend fun addTask(toDoTask: ToDoTask) = withContext(ioDispatcher) {
        toDoDao.addTask(toDoTask)
    }

    override suspend fun updateTask(toDoTask: ToDoTask) = withContext(ioDispatcher) {
        toDoDao.updateTask(toDoTask)
    }

    override suspend fun deleteTask(toDoTask: ToDoTask) = withContext(ioDispatcher) {
        toDoDao.deleteTask(toDoTask)
    }

    override suspend fun deleteAllTasks() = withContext(ioDispatcher) {
        toDoDao.deleteAllTasks()
    }

    override fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>> {
        return toDoDao.searchDatabase(searchQuery)
    }
}
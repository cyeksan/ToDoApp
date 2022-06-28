package com.csappgenerator.todoapp.domain.use_case

import com.csappgenerator.todoapp.domain.model.ToDoTask
import com.csappgenerator.todoapp.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow

class GetAllTasks(private val toDoRepository: ToDoRepository) {
    operator fun invoke() : Flow<List<ToDoTask>> {
        return toDoRepository.getAllTasks()
    }
}
package com.csappgenerator.todoapp.domain.use_case

import com.csappgenerator.todoapp.domain.repository.ToDoRepository

class DeleteAllTasks(private val toDoRepository: ToDoRepository) {
    suspend operator fun invoke() {
        toDoRepository.deleteAllTasks()
    }
}
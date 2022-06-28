package com.csappgenerator.todoapp.domain.use_case

import com.csappgenerator.todoapp.domain.model.ToDoTask
import com.csappgenerator.todoapp.domain.repository.ToDoRepository

class DeleteTask(private val toDoRepository: ToDoRepository) {

    suspend operator fun invoke(toDoTask: ToDoTask) {
        toDoRepository.deleteTask(toDoTask)
    }
}
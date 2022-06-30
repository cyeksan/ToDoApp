package com.csappgenerator.todoapp.domain.use_case

import com.csappgenerator.todoapp.domain.model.ToDoTask
import com.csappgenerator.todoapp.domain.repository.ToDoRepository

class GetSelectedTask(private val toDoRepository: ToDoRepository) {
    suspend operator fun invoke(taskId: Int): ToDoTask {
        return toDoRepository.getSelectedTask(taskId)
    }
}
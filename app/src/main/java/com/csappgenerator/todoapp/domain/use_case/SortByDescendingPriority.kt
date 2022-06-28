package com.csappgenerator.todoapp.domain.use_case

import com.csappgenerator.todoapp.domain.model.ToDoTask
import com.csappgenerator.todoapp.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow

class SortByDescendingPriority(private val repository: ToDoRepository) {
    operator fun invoke() : Flow<List<ToDoTask>> {
        return repository.sortByDescendingPriority()
    }
}
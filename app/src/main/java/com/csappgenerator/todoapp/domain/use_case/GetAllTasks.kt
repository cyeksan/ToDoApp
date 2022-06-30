package com.csappgenerator.todoapp.domain.use_case

import com.csappgenerator.todoapp.domain.model.ToDoTask
import com.csappgenerator.todoapp.domain.repository.ToDoRepository
import com.csappgenerator.todoapp.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllTasks(private val toDoRepository: ToDoRepository) {
    operator fun invoke(
        orderType: OrderType
    ): Flow<List<ToDoTask>> {
        return toDoRepository.getAllTasks().map { tasks ->
            when (orderType) {
                is OrderType.Ascending -> {
                    tasks.sortedBy {
                        it.priority
                    }
                }
                is OrderType.Descending -> {
                    tasks.sortedByDescending {
                        it.priority
                    }
                }
                is OrderType.None -> {
                    tasks
                }
            }

        }
    }
}
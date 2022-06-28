package com.csappgenerator.todoapp.domain.use_case

import com.csappgenerator.todoapp.domain.model.InvalidToDoTaskException
import com.csappgenerator.todoapp.domain.model.ToDoTask
import com.csappgenerator.todoapp.domain.repository.ToDoRepository
import com.csappgenerator.todoapp.util.Constants.DESCRIPTION_NOT_EMPTY_ERROR
import com.csappgenerator.todoapp.util.Constants.TITLE_NOT_EMPTY_ERROR

class AddTask(private val toDoRepository: ToDoRepository) {
    @Throws(InvalidToDoTaskException::class)

    suspend operator fun invoke(toDoTask: ToDoTask) {
        if (toDoTask.title.isBlank())
            throw InvalidToDoTaskException(TITLE_NOT_EMPTY_ERROR)

        if (toDoTask.description.isBlank())
            throw InvalidToDoTaskException(DESCRIPTION_NOT_EMPTY_ERROR)

        toDoRepository.addTask(toDoTask)
    }
}
package com.csappgenerator.todoapp.domain.use_case.wrapper

import com.csappgenerator.todoapp.domain.use_case.AddTask
import com.csappgenerator.todoapp.domain.use_case.DeleteTask
import com.csappgenerator.todoapp.domain.use_case.GetSelectedTask
import com.csappgenerator.todoapp.domain.use_case.UpdateTask

data class TaskUseCases(
    val addTask: AddTask,
    val deleteTask: DeleteTask,
    val getSelectedTask: GetSelectedTask,
    val updateTask: UpdateTask
)
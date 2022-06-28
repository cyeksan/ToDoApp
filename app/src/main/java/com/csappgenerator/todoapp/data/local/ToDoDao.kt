package com.csappgenerator.todoapp.data.local

import androidx.room.*
import com.csappgenerator.todoapp.domain.model.ToDoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo_table")
    fun getAllTasks(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo_table WHERE id = :taskId")
    suspend fun getSelectedTask(taskId: Int): ToDoTask

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(toDoTask: ToDoTask)

    @Update
    suspend fun updateTask(toDoTask: ToDoTask)

    @Delete
    suspend fun deleteTask(toDoTask: ToDoTask)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>>

    @Query(
        "SELECT * FROM TODO_TABLE ORDER BY CASE " +
                "WHEN priority LIKE 'L%' THEN 1 " +
                "WHEN priority LIKE 'M%' THEN 2 " +
                "WHEN priority LIKE 'H%' THEN 3 " +
                "END"
    )
    fun sortByAscendingPriority(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM TODO_TABLE ORDER BY CASE " +
            "WHEN priority LIKE 'H%' THEN 1 " +
            "WHEN priority LIKE 'M%' THEN 2 " +
            "WHEN priority LIKE 'L%' THEN 3 " +
            "END")
    fun sortByDescendingPriority(): Flow<List<ToDoTask>>
}
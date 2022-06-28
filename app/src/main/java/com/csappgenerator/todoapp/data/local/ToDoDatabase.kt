package com.csappgenerator.todoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.csappgenerator.todoapp.domain.model.ToDoTask
import com.csappgenerator.todoapp.util.Constants

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase : RoomDatabase() {
    abstract val toDoDao: ToDoDao

    companion object {
        const val DATABASE_NAME = Constants.DATABASE_NAME
    }
}
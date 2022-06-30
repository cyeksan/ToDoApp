package com.csappgenerator.todoapp.di

import android.content.Context
import androidx.compose.runtime.*
import androidx.room.Room
import com.csappgenerator.todoapp.data.local.ToDoDatabase
import com.csappgenerator.todoapp.data.repository.DataStoreRepository
import com.csappgenerator.todoapp.data.repository.ToDoRepositoryImpl
import com.csappgenerator.todoapp.domain.repository.ToDoRepository
import com.csappgenerator.todoapp.domain.use_case.*
import com.csappgenerator.todoapp.domain.use_case.wrapper.ListUseCases
import com.csappgenerator.todoapp.domain.use_case.wrapper.TaskUseCases
import com.csappgenerator.todoapp.presentation.common.SnackBarState
import com.csappgenerator.todoapp.presentation.list.state.SearchBarState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ToDoDatabase::class.java,
        ToDoDatabase.DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideToDoRepository(db: ToDoDatabase): ToDoRepository {
        return ToDoRepositoryImpl(db.toDoDao)
    }
    @Singleton
    @Provides
    fun provideReadStoreState() : Flow<String> {
        return flow {  }
    }

    @Singleton
    @Provides
    fun provideDataStoreRepository(@ApplicationContext context: Context
    ): DataStoreRepository {
        return DataStoreRepository(context)
    }

    @Singleton
    @Provides
    fun provideListUseCases(toDoRepository: ToDoRepository): ListUseCases {
        return ListUseCases(
            addTask = AddTask(toDoRepository),
            deleteTask = DeleteTask(toDoRepository),
            deleteAllTasks = DeleteAllTasks(toDoRepository),
            getAllTasks = GetAllTasks(toDoRepository),
            getSelectedTask = GetSelectedTask(toDoRepository),
            searchDatabase = SearchDatabase(toDoRepository)
        )
    }

    @Singleton
    @Provides
    fun provideTaskUseCases(toDoRepository: ToDoRepository): TaskUseCases {
        return TaskUseCases(
            addTask = AddTask(toDoRepository),
            deleteTask = DeleteTask(toDoRepository),
            getSelectedTask = GetSelectedTask(toDoRepository),
            updateTask = UpdateTask(toDoRepository)
        )
    }

    @Singleton
    @Provides
    fun provideUpdateState() : MutableState<SnackBarState> {
       return mutableStateOf(SnackBarState.Idle)
    }

    @Singleton
    @Provides
    fun provideCloseSearchBarState() : MutableState<SearchBarState> {
        return mutableStateOf(SearchBarState.SearchBarOpened)
    }
}
package com.csappgenerator.todoapp.di

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.csappgenerator.todoapp.data.local.ToDoDatabase
import com.csappgenerator.todoapp.domain.repository.ToDoRepository
import com.csappgenerator.todoapp.domain.use_case.*
import com.csappgenerator.todoapp.domain.use_case.wrapper.ListUseCases
import com.csappgenerator.todoapp.domain.use_case.wrapper.TaskUseCases
import com.csappgenerator.todoapp.presentation.common.SnackBarState
import com.csappgenerator.todoapp.presentation.list.state.SearchBarState
import com.csappgenerator.todoapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun provideSnackBarState(): MutableState<SnackBarState> {
        return mutableStateOf(SnackBarState.Idle)
    }

    @Singleton
    @Provides
    fun provideSearchBarState(): MutableState<SearchBarState> {
        return mutableStateOf(SearchBarState.SearchBarClosed)
    }

    @Singleton
    @Provides
    fun provideDataStorePreferences(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(Constants.PREFERENCE_NAME) })
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

}
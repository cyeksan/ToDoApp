package com.csappgenerator.todoapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.csappgenerator.todoapp.data.local.ToDoDatabase
import com.csappgenerator.todoapp.data.repository.DataStoreRepositoryImpl
import com.csappgenerator.todoapp.data.repository.ToDoRepositoryImpl
import com.csappgenerator.todoapp.domain.repository.DataStoreRepository
import com.csappgenerator.todoapp.domain.repository.ToDoRepository
import com.csappgenerator.todoapp.domain.use_case.*
import com.csappgenerator.todoapp.domain.use_case.wrapper.ListUseCases
import com.csappgenerator.todoapp.domain.use_case.wrapper.TaskUseCases
import com.csappgenerator.todoapp.util.Constants.PREFERENCE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideToDoRepository(db: ToDoDatabase): ToDoRepository {
        return ToDoRepositoryImpl(db.toDoDao)
    }

    @Singleton
    @Provides
    fun provideDataStorePreferences(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(PREFERENCE_NAME) })
    }

    @Singleton
    @Provides
    fun provideDataStoreRepository(
        dataStore: DataStore<Preferences>
    ): DataStoreRepository {
        return DataStoreRepositoryImpl(dataStore)
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
package com.csappgenerator.todoapp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.csappgenerator.todoapp.data.local.ToDoDatabase
import com.csappgenerator.todoapp.data.repository.DataStoreRepositoryImpl
import com.csappgenerator.todoapp.data.repository.ToDoRepositoryImpl
import com.csappgenerator.todoapp.domain.repository.DataStoreRepository
import com.csappgenerator.todoapp.domain.repository.ToDoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideDataStoreRepository(
        dataStore: DataStore<Preferences>
    ): DataStoreRepository {
        return DataStoreRepositoryImpl(dataStore)
    }
}
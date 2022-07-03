package com.csappgenerator.todoapp.di

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.room.Room
import com.csappgenerator.todoapp.data.local.ToDoDatabase
import com.csappgenerator.todoapp.presentation.common.SnackBarState
import com.csappgenerator.todoapp.presentation.list.state.SearchBarState
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
}
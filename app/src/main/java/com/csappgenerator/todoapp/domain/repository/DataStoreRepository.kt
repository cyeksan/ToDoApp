package com.csappgenerator.todoapp.domain.repository

import com.csappgenerator.todoapp.util.Priority
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveSortState(priority: Priority)
    fun readSortState(): Flow<String>
}
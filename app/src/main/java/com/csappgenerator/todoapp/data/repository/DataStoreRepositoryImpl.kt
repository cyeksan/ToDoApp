package com.csappgenerator.todoapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.csappgenerator.todoapp.domain.repository.DataStoreRepository
import com.csappgenerator.todoapp.util.Constants.PREFERENCE_KEY
import com.csappgenerator.todoapp.util.Priority
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@ViewModelScoped
class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DataStoreRepository {
    private object PreferenceKey {
        val sortKey = stringPreferencesKey(PREFERENCE_KEY)
    }

    override suspend fun saveSortState(priority: Priority) {
        withContext(ioDispatcher) {
            dataStore.edit { preference ->
                preference[PreferenceKey.sortKey] = priority.name
            }
        }
    }

    override fun readSortState(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                    return@catch
                }
                throw exception
            }
            .map { preferences ->
                preferences[PreferenceKey.sortKey] ?: Priority.NONE.name
            }
    }
}
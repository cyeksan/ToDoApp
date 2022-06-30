package com.csappgenerator.todoapp.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.csappgenerator.todoapp.util.Constants.PREFERENCE_KEY
import com.csappgenerator.todoapp.util.Priority
import com.csappgenerator.todoapp.util.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@ViewModelScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private object PreferenceKey {
        val sortKey = stringPreferencesKey(PREFERENCE_KEY)
    }

    private val dataStore = context.dataStore

    suspend fun persistSortState(priority: Priority): Unit = withContext(ioDispatcher) {
        dataStore.edit { preference ->
            preference[PreferenceKey.sortKey] = priority.name
        }
    }

    val readSortState = dataStore.data
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
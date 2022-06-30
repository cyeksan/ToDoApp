package com.csappgenerator.todoapp.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import com.csappgenerator.todoapp.util.Constants.PREFERENCE_NAME

fun NavController.navigateToSpecificTask(taskId: Int) {
    this.navigate(
        Screen.TaskScreen.route +
                "?taskId=${taskId}"
    )
}

fun NavController.navigateToNewTask() {
    this.navigate(
        Screen.TaskScreen.route
    )
}

fun NavController.navigateFromSplashToList() {
    this.navigate(
        Screen.ListScreen.route
    )
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)


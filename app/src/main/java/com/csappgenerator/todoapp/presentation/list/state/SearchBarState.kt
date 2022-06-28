package com.csappgenerator.todoapp.presentation.list.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class SearchBarState {
    object SearchBarOpened : SearchBarState()
    object SearchBarClosed : SearchBarState()
    val searchBarText: MutableState<String> = mutableStateOf("")
}
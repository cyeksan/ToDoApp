package com.csappgenerator.todoapp.presentation.list.event

sealed class SearchBarEvent {
    object OpenSearchBar : SearchBarEvent()
    object CloseSearchBar : SearchBarEvent()
    data class SearchBarOnValueChanged(val value: String) : SearchBarEvent()
}

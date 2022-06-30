package com.csappgenerator.todoapp.presentation.task.event

sealed class UiEvent {
    data class ShowErrorSnackBar(val message: String) : UiEvent()
    object SaveTask : UiEvent()
    object ExitTask : UiEvent()
}

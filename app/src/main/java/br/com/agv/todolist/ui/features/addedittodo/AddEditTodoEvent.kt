package br.com.agv.todolist.ui.features.addedittodo

sealed interface AddEditTodoEvent {
    data class TitleChanged(val title: String): AddEditTodoEvent
    data class DescriptionChanged(val description: String): AddEditTodoEvent
    data object Save: AddEditTodoEvent
}

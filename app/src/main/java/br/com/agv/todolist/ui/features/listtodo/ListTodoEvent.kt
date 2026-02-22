package br.com.agv.todolist.ui.features.listtodo

sealed interface ListTodoEvent {
    data class Delete(val id: Long): ListTodoEvent
    data class CompleteChanged(val id: Long, val isCompleted: Boolean): ListTodoEvent
    data class AddEdit(val id: Long?): ListTodoEvent
}
package br.com.agv.todolist.ui.features.listtodo

sealed interface ListTodoEvent {
    data class DeleteTodo(val todoId: Long): ListTodoEvent
    data class CompleteTodo(val todoId: Long, val isCompleted: Boolean): ListTodoEvent
    data class AddEditTodo(val todoId: Long?): ListTodoEvent
}
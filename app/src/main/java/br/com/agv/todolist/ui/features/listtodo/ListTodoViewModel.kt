package br.com.agv.todolist.ui.features.listtodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.agv.todolist.data.TodoRepository
import br.com.agv.todolist.navigation.AddEditTodo
import br.com.agv.todolist.ui.UIEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListTodoViewModel(
    private val repository: TodoRepository
): ViewModel() {
    val todos = repository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ListTodoEvent) {
        when (event) {
            is ListTodoEvent.CompleteTodo -> {
                completeTodo(event.todoId, event.isCompleted)
            }
            is ListTodoEvent.DeleteTodo -> {
                deleteTodo(event.todoId)
            }
            is ListTodoEvent.AddEditTodo -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UIEvent.Navigate(AddEditTodo(event.todoId))
                    )
                }
            }
        }
    }

    private fun completeTodo(todoId: Long, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.updateCompleted(todoId, isCompleted)
        }
    }

    private fun deleteTodo(todoId: Long) {
        viewModelScope.launch {
            repository.delete(todoId)
        }
    }



}
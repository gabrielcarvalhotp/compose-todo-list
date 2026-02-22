package br.com.agv.todolist.ui.features.listtodo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.agv.todolist.data.TodoRepository
import br.com.agv.todolist.navigation.AddEditTodoRoute
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
            is ListTodoEvent.CompleteChanged -> {
                completeTodo(event.id, event.isCompleted)
            }
            is ListTodoEvent.Delete -> {
                deleteTodo(event.id)
            }
            is ListTodoEvent.AddEdit -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UIEvent.Navigate(AddEditTodoRoute(event.id))
                    )
                }
            }
        }
    }

    private fun completeTodo(id: Long, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.updateCompleted(id, isCompleted)
        }
    }

    private fun deleteTodo(id: Long) {
        viewModelScope.launch {
            repository.delete(id)
        }
    }
}
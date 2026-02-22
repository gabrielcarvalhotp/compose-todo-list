package br.com.agv.todolist.ui.features.addedittodo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.agv.myapplication.R
import br.com.agv.todolist.data.TodoRepository
import br.com.agv.todolist.ui.UIEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AddEditTodoViewModel(
    private val repository: TodoRepository
): ViewModel() {

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf<String?>(null)
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AddEditTodoEvent) {
        when(event) {
            is AddEditTodoEvent.TitleChanged -> {
                title = event.title
            }
            is AddEditTodoEvent.DescriptionChanged -> {
                description = event.description
            }
            is AddEditTodoEvent.Save -> {
                saveTodo()
            }
        }
    }

    private fun saveTodo() {
        viewModelScope.launch {
            if (title.isBlank()) {
                _uiEvent.send(UIEvent.ShowSnackbar("Title cannot be empty"))
                return@launch
            }
            repository.insert(title, description)
            _uiEvent.send(UIEvent.NavigateBack)
        }
    }
}
package br.com.agv.todolist.ui.features.listtodo

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.agv.myapplication.R
import br.com.agv.todolist.data.TodoDatabaseProvider
import br.com.agv.todolist.data.TodoRepositoryImpl
import br.com.agv.todolist.domain.Todo
import br.com.agv.todolist.domain.todo1
import br.com.agv.todolist.domain.todo2
import br.com.agv.todolist.domain.todo3
import br.com.agv.todolist.navigation.AddEditTodo
import br.com.agv.todolist.ui.UIEvent
import br.com.agv.todolist.ui.components.TodoItem
import br.com.agv.todolist.ui.features.addedittodo.AddEditTodoEvent
import br.com.agv.todolist.ui.features.addedittodo.AddEditTodoViewModel
import br.com.agv.todolist.ui.theme.TodoListTheme

@Composable
fun ListTodoScreen(
    onNavigateToAddOrEditTodo: (Long?) -> Unit
) {
    val context = LocalContext.current.applicationContext
    val database = TodoDatabaseProvider.provide(context)
    val repository = TodoRepositoryImpl(
        todoDao = database.todoDao
    )

    val viewModel = viewModel<ListTodoViewModel> {
        ListTodoViewModel(repository)
    }

    val todos by viewModel.todos.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UIEvent.Navigate<*> -> {
                    if (uiEvent.route is AddEditTodo) {
                        onNavigateToAddOrEditTodo(uiEvent.route.id)
                    }
                }
                UIEvent.NavigateBack -> {

                }
                is UIEvent.ShowSnackbar -> {

                }
            }
        }
    }

    ListTodoContent(
        todos = todos,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun ListTodoContent(
    todos: List<Todo>,
    onEvent: (ListTodoEvent) -> Unit = { },
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(ListTodoEvent.AddEditTodo(null)) }) {
                Icon(
                    painterResource(R.drawable.ic_add_24px),
                    contentDescription = stringResource(R.string.add)
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.consumeWindowInsets(innerPadding),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(todos) { index, todo ->
                TodoItem(
                    todo = todo,
                    onCompletedChange = { isChecked ->
                        onEvent(ListTodoEvent.CompleteTodo(todo.id, isChecked))
                    },
                        onDelete = { onEvent(ListTodoEvent.DeleteTodo(todo.id)) },
                        onItemClick = { onEvent(ListTodoEvent.AddEditTodo(todo.id)) },
                    )

                if (index < todos.lastIndex) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun ListTodoContentPreview() {
    TodoListTheme {
        ListTodoContent(
            listOf(todo1, todo2, todo3)
        )
    }
}
package br.com.agv.todolist.ui.features

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.agv.myapplication.R
import br.com.agv.todolist.domain.Todo
import br.com.agv.todolist.domain.todo1
import br.com.agv.todolist.domain.todo2
import br.com.agv.todolist.domain.todo3
import br.com.agv.todolist.ui.components.TodoItem
import br.com.agv.todolist.ui.theme.TodoListTheme

@Composable
fun ListTodoScreen(
    onNavigateToAddOrEditTodo: (Long?) -> Unit,
) {
    ListTodoContent(
        todos = emptyList(),
        onNavigateToAddOrEditTodo = onNavigateToAddOrEditTodo
    )
}

@Composable
fun ListTodoContent(
    todos: List<Todo>,
    onNavigateToAddOrEditTodo: (Long?) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToAddOrEditTodo(null) }) {
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
                    onCompletedChange = { },
                    onDelete = { },
                    onItemClick = { onNavigateToAddOrEditTodo(todo.id) },
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
            listOf(todo1, todo2, todo3), { }
        )
    }
}
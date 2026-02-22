package br.com.agv.todolist.ui.features.addedittodo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import br.com.agv.todolist.ui.theme.TodoListTheme

@Composable
fun AddEditTodoScreen() {
    val context = LocalContext.current.applicationContext
    val database = TodoDatabaseProvider.provide(context)
    val repository = TodoRepositoryImpl(
        todoDao = database.todoDao
    )

    val viewModel = viewModel<AddEditTodoViewModel> {
        AddEditTodoViewModel(repository)
    }

    val title = viewModel.title
    val description = viewModel.description

    AddEditTodoContent(
        title = title,
        description = description,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun AddEditTodoContent(
    title: String = "",
    description: String? = null,
    onEvent: (AddEditTodoEvent) -> Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(AddEditTodoEvent.Save) }) {
                Icon(
                    painterResource(R.drawable.ic_check_24px),
                    contentDescription = stringResource(R.string.add)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .padding(16.dp),
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { onEvent(AddEditTodoEvent.TitleChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.title)) },
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = description ?: "",
                onValueChange = { onEvent(AddEditTodoEvent.DescriptionChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.description)) },
            )
        }
    }
}

@Preview
@Composable
private fun AddEditTodoContentPreview() {
    TodoListTheme {
        AddEditTodoContent()
    }
}
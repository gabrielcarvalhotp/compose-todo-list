package br.com.agv.todolist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import br.com.agv.todolist.ui.features.addedittodo.AddEditTodoScreen
import br.com.agv.todolist.ui.features.listtodo.ListTodoScreen
import kotlinx.serialization.Serializable

@Serializable
object TodoList

@Serializable
data class AddEditTodo(var id: Long? = null) {
}

@Composable
fun TodoNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = TodoList) {
        composable<TodoList> {
            ListTodoScreen(
                onNavigateToAddOrEditTodo = { id ->
                    navController.navigate(route = AddEditTodo(id = id))
                }
            )
        }
        composable<AddEditTodo> { backStackEntry ->
            val id = backStackEntry.toRoute<AddEditTodo>().id
            AddEditTodoScreen(
                id = id,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
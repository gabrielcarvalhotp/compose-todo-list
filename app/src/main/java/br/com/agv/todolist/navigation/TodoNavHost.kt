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
object TodoListRoute

@Serializable
data class AddEditTodoRoute(var id: Long? = null) {
}

@Composable
fun TodoNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = TodoListRoute) {
        composable<TodoListRoute> {
            ListTodoScreen(
                onNavigateToAddOrEditTodo = { id ->
                    navController.navigate(route = AddEditTodoRoute(id = id))
                }
            )
        }
        composable<AddEditTodoRoute> { backStackEntry ->
            val id = backStackEntry.toRoute<AddEditTodoRoute>().id
            AddEditTodoScreen(
                id = id,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
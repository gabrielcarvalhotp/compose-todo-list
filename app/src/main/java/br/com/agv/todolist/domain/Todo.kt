package br.com.agv.todolist.domain

data class Todo(
    val id: Long,
    val title: String,
    val description: String?,
    val isCompleted: Boolean,
)

// fake objects
val todo1 = Todo(
    id = 1,
    title = "Title 1",
    description = "Description 1",
    isCompleted = false
)

val todo2 = Todo(
    id = 2,
    title = "Title 2",
    description = "Description 2",
    isCompleted = false
)

val todo3 = Todo(
    id = 3,
    title = "Title 3",
    description = "Description 3",
    isCompleted = true
)

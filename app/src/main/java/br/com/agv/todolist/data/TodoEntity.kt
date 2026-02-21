package br.com.agv.todolist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.agv.todolist.domain.Todo

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String?,
    var isCompleted: Boolean
)

fun TodoEntity.toDomain(): Todo {
    return Todo(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted
    )
}

package br.com.agv.todolist.data

import br.com.agv.todolist.domain.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun save(id: Long?, title: String, description: String?)

    suspend fun updateCompleted(id: Long, isCompleted: Boolean)

    suspend fun delete(id: Long)

    fun getAll(): Flow<List<Todo>>

    suspend fun getBy(id: Long): Todo?
}
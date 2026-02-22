package br.com.agv.todolist.data

import br.com.agv.todolist.domain.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val todoDao: TodoDao
): TodoRepository {

    override suspend fun save(id: Long?, title: String, description: String?) {
        val entity = id?.let { id ->
            todoDao.getById(id).copy(
                title = title,
                description = description
            )
        } ?: TodoEntity(
            title = title,
            description = description,
            isCompleted = false
        )
        todoDao.save(entity)
    }

    override suspend fun updateCompleted(id: Long, isCompleted: Boolean) {
        val entity = todoDao.getById(id)
        entity.isCompleted = isCompleted
        todoDao.save(entity)
    }

    override suspend fun delete(id: Long) {
        todoDao.deleteById(id)
    }

    override fun getAll(): Flow<List<Todo>> {
        return todoDao.getAll().map { entities ->
            entities.map { entity ->
                entity.toDomain()
            }
        }
    }

    override suspend fun getBy(id: Long): Todo {
        return todoDao.getById(id).toDomain()
    }

}
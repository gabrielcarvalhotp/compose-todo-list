package br.com.agv.todolist.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos")
    fun getAll(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todos WHERE id = :id")
    fun getById(id: Long): TodoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(todo: TodoEntity)

    @Query("DELETE FROM todos WHERE id = :id")
    fun deleteById(id: Long)
}
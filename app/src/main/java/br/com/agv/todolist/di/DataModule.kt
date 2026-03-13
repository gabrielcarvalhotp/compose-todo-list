package br.com.agv.todolist.di

import android.app.Application
import br.com.agv.todolist.data.TodoDao
import br.com.agv.todolist.data.TodoDatabase
import br.com.agv.todolist.data.TodoDatabaseProvider
import br.com.agv.todolist.data.TodoRepository
import br.com.agv.todolist.data.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(application: Application): TodoDatabase {
        return TodoDatabaseProvider.provide(application)
    }

    @Provides
    @Singleton
    fun provideTodoDao(database: TodoDatabase): TodoDao {
        return database.todoDao
    }

    @Provides
    @Singleton
    fun provideTodoRepository(todoDao: TodoDao): TodoRepository {
        return TodoRepositoryImpl(todoDao)
    }

}
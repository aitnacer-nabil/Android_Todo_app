package com.example.todo.data.repository

import androidx.lifecycle.LiveData
import com.example.todo.data.TodoDao
import com.example.todo.data.models.TodoData

class DatabaseRepository(private val todoDao: TodoDao) {

    val getAllData: LiveData<List<TodoData>> = todoDao.getAllData()

    suspend fun insertData(todoData: TodoData) {
        todoDao.insert(todoData)
    }
}
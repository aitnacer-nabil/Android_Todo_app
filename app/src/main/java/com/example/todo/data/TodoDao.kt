package com.example.todo.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todo.data.models.Priority
import com.example.todo.data.models.TodoData

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todoData: TodoData)

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<TodoData>>

    /**
     * Updates an existing Todo item in the database.
     *
     * @param todo The Todo object containing the updated information.
     * @return The number of rows updated in the database.
     */
    @Query("UPDATE todo_table SET title = :title, description = :description, priority = :priority, completed = :completed WHERE id = :id")
    suspend fun updateTodo(todo: TodoData): Int

}
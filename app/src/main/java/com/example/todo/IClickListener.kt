package com.example.todo

import com.example.todo.data.models.TodoData

interface IClickListener {
    fun onClick(todoData: TodoData)

}
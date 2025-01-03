package com.vijay.composetasks.TodoList2

import java.time.Instant
import java.util.Date

object TodoTaskManager {

    private val todoList = mutableListOf<Data>()

    fun getAllTodo():List<Data>{
        return todoList
    }

    fun addTodo(data: String){
        todoList.add(Data(System.currentTimeMillis().toInt(),data, Date.from(Instant.now())))
    }

    fun deleteTodo(id:Int){
        todoList.removeIf {
            it.id == id
        }
    }
}
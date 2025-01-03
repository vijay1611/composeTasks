package com.vijay.composetasks.TodoList2.database

import android.app.Application
import androidx.room.Room

class TodoApplication:Application() {

    companion object{
        lateinit var todoDatabase: TodoDatabase
    }

    override fun onCreate() {
        super.onCreate()
       todoDatabase =  Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            TodoDatabase.name
        ).build()
    }
}
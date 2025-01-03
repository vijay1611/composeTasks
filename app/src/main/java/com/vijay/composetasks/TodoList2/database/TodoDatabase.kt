package com.vijay.composetasks.TodoList2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vijay.composetasks.TodoList2.Data

@Database(entities = [Data::class], version = 1)
@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase()  {

    companion object{
       const val name = "todo_app"
    }
     abstract fun getDao() : TodoDao
}
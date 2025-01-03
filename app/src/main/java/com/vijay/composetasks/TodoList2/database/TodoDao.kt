package com.vijay.composetasks.TodoList2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.vijay.composetasks.TodoList2.Data

@Dao
interface TodoDao {
    @Query("select * from dataTabale")
    fun getData():LiveData<List<Data>>

    @Insert
    fun addData(data:Data)

    @Query("DELETE FROM dataTabale WHERE id = :id")
    fun deleteData(id:Int)
}
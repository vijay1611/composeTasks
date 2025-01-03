package com.vijay.composetasks.TodoList2.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date

class Converters {

    @TypeConverter
    fun fromDate(date:Date):Long{
        return date.time
    }

    @TypeConverter
    fun toDate(date:Long):Date{
        return Date(date)
    }
}
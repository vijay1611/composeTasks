package com.vijay.composetasks.TodoList2

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.Date

@Entity(tableName = "dataTabale")
data class Data(
    @PrimaryKey(autoGenerate = true)
    var id : Int=0,
    var title : String,
    var date : Date,
    var image : ByteArray
)


fun getLocalData():List<Data>{

  /*  return listOf(
        Data(1,"vijay",Date.from(Instant.now()),),
        Data(2,"raja",Date.from(Instant.now())),
        Data(3,"ranjith",Date.from(Instant.now())),
        )*/
    return listOf()
}
package com.vijay.composetasks.TodoList2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vijay.composetasks.TodoList2.database.TodoApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class TodoViewModel:ViewModel() {
    var dao = TodoApplication.todoDatabase.getDao()
    private var _todoList = MutableLiveData<List<Data>>()
    val todoList : LiveData<List<Data>> = dao.getData()

//    fun getALlTodo(){
//        _todoList.value = dao.getData()
//    }

    fun addTodo(data: String,uri:ByteArray){
        viewModelScope.launch(Dispatchers.IO) {
            dao.addData(Data(title = data, date = Date.from(Instant.now()), image = uri))
        }
        //getALlTodo()
    }

    fun deleteTodo(id:Int){
        viewModelScope.launch(Dispatchers.IO){
            dao.deleteData(id)
        }
        //getALlTodo()
    }
}
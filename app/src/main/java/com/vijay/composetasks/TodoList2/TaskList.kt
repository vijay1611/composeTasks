package com.vijay.composetasks.TodoList2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vijay.composetasks.TodoTask.Task
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskList(
    modifier:Modifier,viewModel: TodoViewModel
) {

    val dataList by viewModel.todoList.observeAsState()

    Column(
        modifier= Modifier
            .fillMaxHeight()
            .padding(6.dp)
    ) {


        var name by remember {
            mutableStateOf("")
        }
       Row (
           modifier= Modifier
               .fillMaxWidth()
               .padding(10.dp),
           horizontalArrangement = Arrangement.SpaceEvenly
       ){
           OutlinedTextField(value = name,
               modifier=Modifier.weight(1f),
               onValueChange = { name =it },)
           Button(
               onClick = {
                   viewModel.addTodo(name)
                   name = ""
               }) {
               Text(text = "Add",)
           }
       }

        dataList?.let {
            LazyColumn(content = {
                itemsIndexed(it){
                        index:Int,item:Data ->
                    TaskData(data = item, onDelete = {
                        viewModel.deleteTodo(item.id)
                    })
                }
            }
            )
        }?: Text(text = "No items yet...",
            modifier= Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 16.sp
                )
        }

    }


@Composable
fun TaskData(data: Data, onDelete : () -> Unit){
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(MaterialTheme.colorScheme.primary)
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Column (
            modifier = Modifier.weight(1f)
        ){
            Text(text = SimpleDateFormat("HH:MM:aa dd/mm", Locale.ENGLISH).format(data.date),
                fontSize = 12.sp,
                color = Color.White)
            Text(text = data.title,
                fontSize = 20.sp,
                color = Color.White)
        }
        IconButton(onClick = { onDelete()}) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.White
            )
        }
    }

}
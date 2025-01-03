package com.vijay.composetasks

import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.vijay.composetasks.TodoList2.TaskList
import com.vijay.composetasks.TodoList2.TodoViewModel
import com.vijay.composetasks.TodoList2.getLocalData
import com.vijay.composetasks.TodoTask.Task
import com.vijay.composetasks.ui.theme.ComposeTasksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[TodoViewModel::class.java]
        setContent {
            ComposeTasksTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    var dataList = getLocalData()
                    //TodoApp()
                    TaskList(modifier = Modifier, viewModel = viewModel)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoApp() {
    var taskList by remember {
        mutableStateOf(mutableListOf<Task>())
    }
    var taskText by remember {
        mutableStateOf("")
    }
    var idCount by remember {
        mutableStateOf(1)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            text = "To-do App",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedTextField(
                value = taskText,
                onValueChange = { taskText = it },
                keyboardActions = KeyboardActions(onDone = {
                    if(taskText.isNotBlank()){
                        taskList = taskList.toMutableList().apply {
                            add(Task(idCount++, taskText, false))
                        }
                    }
                })

                )
            Button(
                onClick = {
                    if (taskText.isNotBlank()) {
                        taskList = taskList.toMutableList().apply {
                            add(Task(idCount++, taskText, false))
                        }
                }},
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = "Add")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(taskList) { task ->
                TodoScreen(
                    task = task,
                    onToggleDone = {
                        task.isDone = !task.isDone
                        taskList = taskList.toMutableList()
                    },
                    onDelete = {
                        taskList.remove(task)
                        taskList = taskList.toMutableList()
                    })
            }
        }

    }
}


@Composable
fun TodoScreen(
    task : Task,
    onToggleDone : () -> Unit,
    onDelete : () -> Unit
){
    Row {
        Checkbox(
            modifier = Modifier.padding(end = 8.dp),
            onCheckedChange = {onToggleDone()},
            checked = task.isDone
        )
        Text(text = task.title,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
            )
        IconButton(onClick = {onDelete() }) {
            Icon(imageVector = Icons.Default.Delete,
                contentDescription = null
                )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeTasksTheme {
        //TodoScreen(task = Task(1,"Vijay",false), onToggleDone = { /*TODO*/ }) {
          TodoApp()
        }
    }

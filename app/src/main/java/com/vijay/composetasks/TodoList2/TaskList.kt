package com.vijay.composetasks.TodoList2

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskList(
    modifier:Modifier,viewModel: TodoViewModel
) {

    val dataList by viewModel.todoList.observeAsState()
    val context = LocalContext.current

    var imageUri = remember {
        mutableStateOf<Uri?>(null)
    }
    var imageBitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }
    val permissionGranted = remember {
        mutableStateOf(false)
    }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = {
            imageBitmap.value = it
        })
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUri.value = it
            Toast.makeText(context, "Image Selected", Toast.LENGTH_SHORT).show()
        }
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            //permissionGranted.value = true
            cameraLauncher.launch()
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
    fun hasPermission(context: Context): Boolean {
        return  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        }
    }




    Column(
        modifier= Modifier
            .fillMaxHeight()
            .padding(6.dp)
    ) {


        var name by remember {
            mutableStateOf("")
        }
       Column (
           modifier= Modifier
               .fillMaxWidth()
               .padding(10.dp)
       ){
           OutlinedTextField(value = name,
               modifier=Modifier.fillMaxWidth(),
               onValueChange = { name =it },)

       }
        Button(onClick = {
            /*if(hasPermission(context)){
                permissionGranted.value = true
            //    showImageSourceDialog(context = context, galleryLauncher = galleryLauncher, cameraLauncher = cameraLauncher)
            }else{*/
                permissionLauncher.launch(Manifest.permission.CAMERA)
            })
        {
            Text(text = "Pick Image")
        }
        Spacer(modifier = Modifier.height(16.dp))
        imageUri.value?.let { uri->
            Text(text = "Selected Image Uri $uri")
        }

         var convertedImage = imageBitmap.value?.let { bitmapToByteArray(imageBitmap.value!!) }
        Button(
            onClick = {
                if (convertedImage != null) {
                    viewModel.addTodo(name,convertedImage)
                    name = ""
                }else{
                    Toast.makeText(context, "Pick image to save", Toast.LENGTH_SHORT).show()
                }

            }) {
            Text(text = "Add",)
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
fun uriToByteArray(context: Context, uri: Uri): ByteArray? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val outputStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream?.read(buffer).also { length = it ?: -1 } != -1) {
            outputStream.write(buffer, 0, length)
        }
        outputStream.toByteArray()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
// Convert Bitmap to ByteArray
fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}

// Convert ByteArray to Bitmap
fun byteArrayToBitmap(byteArray: ByteArray): Bitmap {
    return android.graphics.BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
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
        var byteArrayImage = byteArrayToBitmap(data.image)
        Image(bitmap = byteArrayImage.asImageBitmap(), contentDescription = null,
            modifier = Modifier.size(100.dp))
        IconButton(onClick = { onDelete()}) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.White
            )
        }
    }

}
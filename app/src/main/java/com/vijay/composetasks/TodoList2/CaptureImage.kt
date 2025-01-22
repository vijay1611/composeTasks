package com.vijay.composetasks.TodoList2

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext

@Composable
fun ImagePickerWithPermission() {
    val context = LocalContext.current

    val imageUri = remember {
        mutableStateOf<Uri?>(null)
    }
    val permissionGranted = remember {
        mutableStateOf(false)
    }

    var bitmapImage = remember {
        mutableStateOf<Bitmap?>(null)
    }

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
            permissionGranted.value = true
            galleryLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        bitmapImage.value?.let {
                Image(bitmap = it.asImageBitmap(), contentDescription = null)
            }
        
        Button(onClick = { permissionLauncher.launch(Manifest.permission.CAMERA)}) {
            Text(text = "Capture Image")
        }
    }
}
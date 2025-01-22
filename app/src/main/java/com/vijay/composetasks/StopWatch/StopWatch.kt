package com.vijay.composetasks.StopWatch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun Stopwatch() {
    var time by remember { mutableStateOf(0L) } // Time in milliseconds
    var isRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (isRunning) {
                delay(100L) // Update every 100 ms
                time += 100L
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = formatTime(time),
            fontSize = 32.sp,
            modifier = Modifier.padding(16.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = { isRunning = true }) {
                Text("Start")
            }

            Button(onClick = { isRunning = false }) {
                Text("Stop")
            }

            Button(onClick = {
                isRunning = false
                time = 0L
            }) {
                Text("Reset")
            }
        }
    }
}
@Composable
fun ReverseStopwatch() {
    var remainingTime by remember { mutableStateOf(60_000L) } // Initial time: 1 minute (60,000 ms)
    var isRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            while (isRunning && remainingTime > 0) {
                delay(100L) // Update every 100 ms
                remainingTime -= 100L
            }
            if (remainingTime <= 0) {
                isRunning = false // Stop when time reaches 0
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = formatTime(remainingTime),
            fontSize = 32.sp,
            modifier = Modifier.padding(16.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = { isRunning = true }) {
                Text("Start")
            }

            Button(onClick = { isRunning = false }) {
                Text("Stop")
            }

            Button(onClick = {
                isRunning = false
                remainingTime = 60_000L // Reset to 1 minute
            }) {
                Text("Reset")
            }
        }
    }
}


// Helper function to format time in mm:ss:SSS
fun formatTime(timeMillis: Long): String {
    val minutes = (timeMillis / 1000) / 60
    val seconds = (timeMillis / 1000) % 60
    val milliseconds = timeMillis % 1000
    return String.format("%02d:%02d:%03d", minutes, seconds, milliseconds)
}
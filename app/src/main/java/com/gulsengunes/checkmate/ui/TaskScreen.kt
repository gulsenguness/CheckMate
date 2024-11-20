package com.gulsengunes.checkmate.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.gulsengunes.checkmate.R
import com.gulsengunes.checkmate.data.SharedPreferencesHelper
import com.gulsengunes.checkmate.model.Task

@Composable
fun TaskScreen(modifier: Modifier = Modifier, context: Context) {
    var taskName by remember { mutableStateOf("") }
    val sharedPreferencesHelper = SharedPreferencesHelper(context)
    val tasks = remember { mutableStateListOf<Task>() }
    val lottie by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation))
    val progress by animateLottieCompositionAsState(
        composition = lottie,
        iterations = LottieConstants.IterateForever
    )

    LaunchedEffect(Unit) {
        tasks.addAll(sharedPreferencesHelper.loadTasks())
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        LottieAnimation(
            composition = lottie,
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)

        )
        Text(
            text = "Task List",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)

        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            TextField(
                value = taskName,
                onValueChange = { taskName = it },
                placeholder = { Text("Add new task") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (taskName.isNotBlank()) {
                    val newTask = Task(id = tasks.size + 1, name = taskName)
                    tasks.add(newTask)
                    sharedPreferencesHelper.saveTasks(tasks)
                    taskName = ""
                }
            }) {
                Text("Add")
            }
        }

        LazyColumn {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onTaskUpdate = { updatedTask ->
                        val updatedTasks = tasks.map {
                            if (it.id == updatedTask.id) updatedTask else it
                        }
                        tasks.clear()
                        tasks.addAll(updatedTasks)
                        sharedPreferencesHelper.saveTasks(tasks)
                    },
                    onTaskDelete = { taskToDelete ->
                        tasks.remove(taskToDelete)
                        sharedPreferencesHelper.saveTasks(tasks)
                    }
                )
            }

        }
    }
}

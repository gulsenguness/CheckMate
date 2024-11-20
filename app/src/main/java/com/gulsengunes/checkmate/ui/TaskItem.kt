package com.gulsengunes.checkmate.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.gulsengunes.checkmate.model.Task

@Composable
fun TaskItem(task: Task, onTaskUpdate: (Task) -> Unit, onTaskDelete: (Task) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = {
                onTaskUpdate(task.copy(isCompleted = it))
            }
        )
        Text(
            text = task.name,
            style = if (task.isCompleted) {
                TextStyle(
                    textDecoration = TextDecoration.LineThrough,
                    color = androidx.compose.ui.graphics.Color.Blue
                )
            } else {
                TextStyle(color = androidx.compose.ui.graphics.Color.Black)
            },
            modifier = Modifier.padding(start = 8.dp)
        )
        IconButton(
            onClick = { onTaskDelete(task) }
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Task",
                tint = androidx.compose.ui.graphics.Color.Black
            )
        }
    }
}
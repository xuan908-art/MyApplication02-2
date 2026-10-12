package com.example.myapplication02_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

// 把isFinished改成MutableState<Boolean>，Compose可以直接监听勾选变化
data class Task(
    val name: String,
    val isFinished: androidx.compose.runtime.MutableState<Boolean>
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TaskScreen()
                }
            }
        }
    }
}

@Composable
fun TaskScreen() {
    val taskList = remember {
        mutableStateListOf(
            Task("学习Column和Row", mutableStateOf(true)),
            Task("学习状态管理", mutableStateOf(false)),
            Task("完成Compose实验", mutableStateOf(false))
        )
    }
    var inputText by remember { mutableStateOf("") }

    // 统计已完成数量
    val finishedCount = taskList.count { it.isFinished.value }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(text = "课程学习任务", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.padding(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = { Text("请输入学习任务") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (inputText.isNotBlank()) {
                    taskList.add(Task(inputText, mutableStateOf(false)))
                    inputText = ""
                }
            }) {
                Text("添加")
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))
        Text(text = "已完成: $finishedCount / ${taskList.size}")
        Spacer(modifier = Modifier.padding(8.dp))

        LazyColumn {
            items(taskList) { task ->
                TaskItem(
                    task = task,
                    onDelete = {
                        taskList.remove(task)
                    }
                )
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = task.isFinished.value,
                onCheckedChange = { newValue ->
                    task.isFinished.value = newValue
                }
            )
            Text(
                text = task.name,
                textDecoration = if (task.isFinished.value) TextDecoration.LineThrough else TextDecoration.None
            )
        }
        Button(onClick = onDelete) {
            Text("删除")
        }
    }
}

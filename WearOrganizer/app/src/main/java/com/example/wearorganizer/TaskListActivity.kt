package com.example.wearorganizer

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Text

// Экран 4: список дел из базы Room, добавление и удаление
class TaskListActivity : ComponentActivity() {

    // список дел и id выбранного дела (при изменении экран обновляется сам)
    private val tasks = mutableStateOf<List<Task>>(ArrayList())
    private val selectedId = mutableStateOf(-1L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ListScreen() }
    }

    // при возврате с формы список обновляется
    override fun onResume() {
        super.onResume()
        loadTasks()
    }

    fun loadTasks() {
        tasks.value = AppDatabase.get(this).taskDao().getAll()
        selectedId.value = -1L
    }

    @Composable
    fun ListScreen() {
        ScreenBox {
            ScalingLazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                item {
                    Text(
                        text = "Мои дела",
                        color = Blue,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (tasks.value.isEmpty()) {
                    item { Text(text = "Пока пусто", color = Gray, fontSize = 12.sp) }
                }

                items(tasks.value.size) { index ->
                    val task = tasks.value[index]
                    val selected = task.id == selectedId.value
                    Chip(
                        label = { Text(text = task.title, maxLines = 1) },
                        secondaryLabel = { Text(text = task.priority) },
                        onClick = { selectedId.value = task.id },
                        colors = if (selected) {
                            ChipDefaults.primaryChipColors()
                        } else {
                            ChipDefaults.secondaryChipColors()
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    Chip(
                        label = { Text("Удалить выбранное") },
                        onClick = { onDeleteClick() },
                        colors = ChipDefaults.secondaryChipColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    Chip(
                        label = { Text("Добавить дело") },
                        onClick = { onAddClick() },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

    fun onAddClick() {
        startActivity(Intent(this, FormActivity::class.java))
    }

    // удаляем выбранное дело
    fun onDeleteClick() {
        if (selectedId.value == -1L) {
            Toast.makeText(this, "Сначала выберите дело", Toast.LENGTH_SHORT).show()
            return
        }

        val dao = AppDatabase.get(this).taskDao()
        for (task in tasks.value) {
            if (task.id == selectedId.value) {
                dao.delete(task)
            }
        }

        Toast.makeText(this, "Дело удалено", Toast.LENGTH_SHORT).show()
        loadTasks()
    }
}

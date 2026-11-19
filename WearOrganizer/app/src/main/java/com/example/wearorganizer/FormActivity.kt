package com.example.wearorganizer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Text

// Экран 3: форма ввода данных
class FormActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { FormScreen() }
    }

    @Composable
    fun FormScreen() {
        val title = remember { mutableStateOf("") }
        val priority = remember { mutableStateOf("Средний") }

        ScreenBox {
            ScalingLazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                item {
                    Text(
                        text = "Новое дело",
                        color = Blue,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                item { InputField(title, "Название") }
                item {
                    // по нажатию приоритет меняется по кругу
                    Chip(
                        label = { Text("Приоритет: " + priority.value) },
                        onClick = { priority.value = nextPriority(priority.value) },
                        colors = ChipDefaults.secondaryChipColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    Chip(
                        label = { Text("Сохранить") },
                        onClick = { onSaveClick(title.value, priority.value) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

    fun nextPriority(current: String): String {
        if (current == "Низкий") {
            return "Средний"
        }
        if (current == "Средний") {
            return "Высокий"
        }
        return "Низкий"
    }

    // нажатие на кнопку "Сохранить"
    fun onSaveClick(title: String, priority: String) {
        val cleanTitle = title.trim()
        if (cleanTitle.isEmpty()) {
            Toast.makeText(this, "Введите название", Toast.LENGTH_SHORT).show()
            return
        }

        // id = 0, номер поставит Room
        val task = Task(0, cleanTitle, priority)
        AppDatabase.get(this).taskDao().insert(task)

        Toast.makeText(this, "Дело добавлено", Toast.LENGTH_SHORT).show()
        finish()
    }
}

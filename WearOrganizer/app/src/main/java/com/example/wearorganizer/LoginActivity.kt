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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.Text

// Экран 1: приветствие и авторизация
class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // если пользователь уже входил, сразу открываем меню
        if (UserStorage.load(this) != null) {
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
            return
        }

        setContent { LoginScreen() }
    }

    @Composable
    fun LoginScreen() {
        val name = remember { mutableStateOf("") }
        val email = remember { mutableStateOf("") }

        ScreenBox {
            ScalingLazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                item {
                    Text(
                        text = "Органайзер",
                        color = Blue,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                item { InputField(name, "Имя") }
                item { InputField(email, "E-mail") }
                item {
                    Chip(
                        label = { Text("Войти") },
                        onClick = { onLoginClick(name.value, email.value) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

    // нажатие на кнопку "Войти"
    fun onLoginClick(name: String, email: String) {
        val cleanName = name.trim()
        val cleanEmail = email.trim()

        // проверка ввода на пустоту
        if (cleanName.isEmpty() || cleanEmail.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        // сохраняем пользователя в SharedPreferences (JSON)
        UserStorage.save(this, User(cleanName, cleanEmail))
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }
}

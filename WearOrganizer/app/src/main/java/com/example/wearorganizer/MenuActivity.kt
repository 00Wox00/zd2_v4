package com.example.wearorganizer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text

// Экран 2: меню со значками
class MenuActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MenuScreen() }
    }

    @Composable
    fun MenuScreen() {
        val user = UserStorage.load(this)
        val hello = if (user != null) "Привет, " + user.name + "!" else "Привет!"

        ScreenBox {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = hello,
                    color = Blue,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row {
                    MenuItem(R.drawable.ic_add, "Добавить", this@MenuActivity::onAddClick)
                    MenuItem(R.drawable.ic_list, "Дела", this@MenuActivity::onListClick)
                    MenuItem(R.drawable.ic_exit, "Выйти", this@MenuActivity::onExitClick)
                }
            }
        }
    }

    fun onAddClick() {
        startActivity(Intent(this, FormActivity::class.java))
    }

    fun onListClick() {
        startActivity(Intent(this, TaskListActivity::class.java))
    }

    fun onExitClick() {
        UserStorage.clear(this)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}

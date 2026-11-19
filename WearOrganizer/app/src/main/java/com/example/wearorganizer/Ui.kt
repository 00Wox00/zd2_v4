package com.example.wearorganizer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text

// Цвета из макета
val Blue = Color(0xFF2B70A8)
val Gray = Color(0xFF6B6B6B)

// Тёмная тема с синим акцентом
@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val colors = Colors(
        primary = Blue,
        onPrimary = Color.White,
        secondary = Blue,
        background = Color.Black,
        surface = Color(0xFF1C1C1C),
        onSurface = Color.White,
        onBackground = Color.White
    )
    MaterialTheme(colors = colors, content = content)
}

// Чёрный фон на весь экран часов
@Composable
fun ScreenBox(content: @Composable () -> Unit) {
    AppTheme {
        Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
            content()
        }
    }
}

// Поле ввода с синей рамкой и подсказкой
@Composable
fun InputField(state: MutableState<String>, hint: String) {
    BasicTextField(
        value = state.value,
        onValueChange = { state.value = it },
        singleLine = true,
        textStyle = TextStyle(color = Color.White, fontSize = 14.sp),
        cursorBrush = SolidColor(Color.White),
        modifier = Modifier.fillMaxWidth(),
        decorationBox = { inner ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Blue, RoundedCornerShape(20.dp))
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                if (state.value.isEmpty()) {
                    Text(text = hint, color = Gray, fontSize = 14.sp)
                }
                inner()
            }
        }
    )
}

// Круглая кнопка меню со значком и подписью
@Composable
fun MenuItem(iconRes: Int, label: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Button(onClick = onClick, modifier = Modifier.size(44.dp)) {
            Icon(painter = painterResource(id = iconRes), contentDescription = label)
        }
        Text(text = label, fontSize = 10.sp, color = Color.White)
    }
}

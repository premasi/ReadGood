package com.rakarguntara.readgood.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TextTitle(text: String = "Read Good"){
    Text(text, style = TextStyle(
        fontWeight = FontWeight.Bold,
        color = Color.Black.copy(0.5f),
        fontSize = 24.sp
    )
    )
}
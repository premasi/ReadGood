package com.rakarguntara.readgood.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FABContent(onTab: () -> Unit) {
    FloatingActionButton(onClick = {
        onTab()
    }, shape = RoundedCornerShape(50.dp), containerColor = Color.Blue){
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Book",
            tint = Color.White)
    }
}
package com.rakarguntara.readgood.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.rakarguntara.readgood.models.BookModel

@Preview
@Composable
fun ListBookCard(bookModel: BookModel = BookModel("a", "asd", "sdsd",
    "sadasd"),  onPressDetails: (String) -> Unit = {}){
    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density
    val spacing = 10.dp
    Card(shape = RoundedCornerShape(25.dp),
        colors = CardColors(Color.White,Color.White,Color.White,Color.White),
        modifier = Modifier.padding(end = 8.dp)
            .height(250.dp)
            .width(200.dp)
            .clickable { onPressDetails.invoke(bookModel.title!!) }
    ) {
        Column(modifier = Modifier.width(screenWidth.dp - (spacing * 2)),
            horizontalAlignment = Alignment.Start) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Image(painter = rememberImagePainter(data = ""), contentDescription = "book potret",
                    modifier = Modifier.height(150.dp).width(100.dp).padding(5.dp))

                Spacer(modifier = Modifier.width(50.dp))

                Column {

                }
            }

        }
    }
}
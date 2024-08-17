package com.rakarguntara.readgood.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.rakarguntara.readgood.models.BookModel

@Composable
fun ListBookCardRow(
    book: BookModel,
    navController: NavController
){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {  }
            .padding(bottom = 8.dp),
        color = Color.White,
        shadowElevation = 5.dp
    ){
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(model = "http://books.google.com/books/content?id=1GjXCQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api", contentDescription = "book potret",
                modifier = Modifier.height(150.dp).width(100.dp).padding(5.dp))

            Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top) {
                Text(book.title.toString(), fontSize = 14.sp, color = Color.Black,
                    fontWeight = FontWeight.Bold)
                Text(book.author.toString(), fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.LightGray,
                    modifier = Modifier.padding(vertical = 4.dp)
                    )
                Text(book.notes.toString(), fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.LightGray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text("[${book.id}]", fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.LightGray
                )
            }

        }
    }
}
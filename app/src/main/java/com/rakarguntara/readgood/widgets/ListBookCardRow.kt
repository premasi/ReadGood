package com.rakarguntara.readgood.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.rakarguntara.readgood.models.BookModel
import com.rakarguntara.readgood.models.ItemsItem
import com.rakarguntara.readgood.navigation.ReadScreens

@Composable
fun ListBookCardRow(
    item: ItemsItem,
    navController: NavController
){
    val imageUrl = if(item.volumeInfo?.imageLinks?.thumbnail == null) {
        "http://books.google.com/books/content?id=1GjXCQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
    } else {
        item.volumeInfo.imageLinks.thumbnail
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(ReadScreens.DetailScreen.name + "/${item.id}")
            }
            .padding(bottom = 8.dp),
        color = Color.White,
        shadowElevation = 5.dp
    ){
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(model = imageUrl, contentDescription = "Potret Buku",
                modifier = Modifier.height(150.dp).width(100.dp).padding(5.dp))

            Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top) {
                Text(item.volumeInfo?.title.toString(), fontSize = 14.sp, color = Color.Black,
                    fontWeight = FontWeight.Bold)
                Text(item.volumeInfo?.authors.toString(), fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.LightGray,
                    modifier = Modifier.padding(vertical = 4.dp)
                    )
                Text(item.volumeInfo?.publishedDate.toString(), fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.LightGray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text("[${item.volumeInfo?.categories}]", fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.LightGray
                )
            }

        }
    }
}

@Composable
fun ListBookCardRow2(
    item: BookModel,
    navController: NavController
){
    val imageUrl = if(item.photoUrl == null) {
        "http://books.google.com/books/content?id=1GjXCQAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api"
    } else {
        item.photoUrl
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(ReadScreens.DetailScreen.name + "/${item.id}")
            }
            .padding(bottom = 8.dp),
        color = Color.White,
        shadowElevation = 5.dp
    ){
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(model = imageUrl, contentDescription = "Potret Buku",
                modifier = Modifier.height(150.dp).width(100.dp).padding(5.dp))

            Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top) {
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth( )) {
                    Text(item.title.toString(), fontSize = 14.sp, color = Color.Black,
                        fontWeight = FontWeight.Bold)
                    if(item.rating >= 3){
                        Icon(Icons.Default.ThumbUp, contentDescription = "like",
                            tint = Color.Green)
                    } else{
                        Box(){}
                    }
                }

                Text(item.author.toString(), fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.LightGray,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(item.published.toString(), fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.LightGray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text("[${item.rating}]", fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.LightGray
                )
            }

        }
    }
}
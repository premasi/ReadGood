package com.rakarguntara.readgood.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.rakarguntara.readgood.models.BookModel
import kotlinx.coroutines.delay

@Composable
fun ListBookCard(bookModel: BookModel,  onPressDetails: (String) -> Unit = {}){
    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density
    val spacing = 10.dp
    val isPressed = remember { mutableStateOf(false) }
    if(isPressed.value){
        LaunchedEffect(Unit){
            delay(100)
            isPressed.value = false
        }
    }
    Card(shape = RoundedCornerShape(25.dp),
        colors = CardColors(
            containerColor = if(isPressed.value) Color.LightGray else Color.White,Color.White,Color.White,Color.White),
        modifier = Modifier.padding(end = 8.dp)
            .width(200.dp)
            .clickable {
                isPressed.value = true
                onPressDetails.invoke(bookModel.id!!)
            }
    ) {
        Column(modifier = Modifier.width(screenWidth.dp - (spacing * 2)),
            horizontalAlignment = Alignment.Start) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                AsyncImage(model = bookModel.photoUrl, contentDescription = "book potret",
                    modifier = Modifier.height(150.dp).width(100.dp).padding(5.dp))

                Spacer(modifier = Modifier.width(50.dp))

                Column(modifier = Modifier.padding(top = 25.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "Fav Icon",
                        modifier = Modifier.padding(bottom = 1.dp),
                        tint = Color.Black)

                    BookRating(score = bookModel.rating.toDouble())


                }
            }

            Text(text = bookModel.title.toString(), modifier = Modifier.padding(4.dp),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)

            Text(text = bookModel.author.toString(), modifier = Modifier.padding(4.dp),
                color = Color.LightGray,
                style = MaterialTheme.typography.bodySmall)

            val isStartedRead = remember {
                mutableStateOf(false)
            }

            Row(horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.fillMaxWidth()) {
                isStartedRead.value = bookModel.startRead != null
                RoundedButton(label = if(isStartedRead.value) "Reading" else "Added", radius = 50)

            }
        }
    }
}

@Composable
fun RoundedButton(
    label: String = "Reading",
    radius: Int = 29,
    onPress: () -> Unit = {}
){
    Surface(modifier = Modifier.clip(shape = RoundedCornerShape(topStartPercent = radius, bottomStartPercent = radius)),
        color = Color.Blue){
        Column(modifier = Modifier.width(90.dp).heightIn(40.dp)
            .clickable { onPress.invoke() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = label, style = TextStyle(
                color = Color.White,
                fontSize = 12.sp
            ))

        }
    }
}

@Composable
fun BookRating(score: Double) {
    Surface(modifier = Modifier.height(70.dp).padding(4.dp),
        shape = RoundedCornerShape(50.dp),
        tonalElevation = 6.dp,
        color = Color.Black
    ){
        Column(modifier = Modifier.padding(4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = Icons.Rounded.Star,
                contentDescription = "Rate Icon",
                modifier = Modifier.padding(3.dp),
                tint = Color.Yellow)
            Text(text = score.toString(), style = MaterialTheme.typography.labelSmall)
        }
    }
}

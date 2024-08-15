package com.rakarguntara.readgood.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.rakarguntara.readgood.components.FABContent
import com.rakarguntara.readgood.models.BookModel
import com.rakarguntara.readgood.widgets.ReadAppBar

@Composable
fun HomeScreen(navController: NavController = NavController(LocalContext.current)) {
    Scaffold(topBar = {
        ReadAppBar("Read Good", navController = navController)
    }, floatingActionButton = {
       FABContent{}
    }) {
        Surface(modifier = Modifier.padding(it).fillMaxSize().padding(horizontal = 8.dp)) {
            HomeContent(navController)
        }
    }
}

@Composable
fun HomeContent(navController: NavController){
    val displayName = if(!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
        FirebaseAuth.getInstance().currentUser?.email?.split("@")!![0]
    } else {
        "N/A"
    }
    Column(modifier = Modifier,
        verticalArrangement = Arrangement.Top) {
        Column (Modifier.fillMaxWidth().align(Alignment.Start)) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text("Welcome back! ", fontWeight = FontWeight.Normal,
                    fontSize = 14.sp)
                Text(displayName, fontWeight = FontWeight.Bold,
                    fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Clip)
            }
            HorizontalDivider(modifier = Modifier.padding(8.dp))
            TitleSection(Modifier,"Progress")
        }

    }
}

@Composable
fun TitleSection(modifier: Modifier, label: String){
    Surface(modifier.fillMaxWidth().padding(8.dp)) {
        Column {
            Text(label, fontSize = 14.sp, fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start)
        }
    }
}

@Composable
fun ReadNow(books: List<BookModel>, navController: NavController){
}



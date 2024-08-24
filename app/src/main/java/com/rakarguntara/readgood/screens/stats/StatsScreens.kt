package com.rakarguntara.readgood.screens.stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.rakarguntara.readgood.models.BookModel
import com.rakarguntara.readgood.viewmodel.home.HomeViewModel
import com.rakarguntara.readgood.widgets.ListBookCardRow2
import com.rakarguntara.readgood.widgets.ReadAppBar

@Composable
fun StatsScreens(navController: NavController, homeViewModel: HomeViewModel = hiltViewModel()) {
    var books: List<BookModel>
    val currentUser = FirebaseAuth.getInstance().currentUser!!

    Scaffold(topBar = {
        ReadAppBar(
            title = "Profile",
            navController = navController,
            showProfile = false,
            icon = Icons.AutoMirrored.Filled.ArrowBack
        ){
            navController.popBackStack()
        }
    }) {
        books = if(!homeViewModel.data.value.data.isNullOrEmpty()){
            homeViewModel.data.value.data!!.filter { book ->
                (book.userId == currentUser.uid)
            }
        } else {
            emptyList()
        }
        val readBookList : List<BookModel> = if(!homeViewModel.data.value.data.isNullOrEmpty()){
            books.filter { book ->
                (book.userId == currentUser.uid) && (book.finishedRead != null)
            }
        } else {
            emptyList()
        }

        val readingBooks = books.filter { book ->
            book.startRead != null && book.finishedRead == null
        }
        Column(verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth().padding(it).padding(16.dp)) {
            Row(modifier = Modifier.padding()) {
                Box(modifier = Modifier.size(45.dp)) {
                    Icon(Icons.Sharp.Person, contentDescription = "icon")
                }
                Text("Hi, ${currentUser.email!!.split("@")[0]
                    .uppercase()}", color = Color.Black)

            }
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                shape = CircleShape
            ) {


                Column(modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalAlignment = Alignment.Start) {
                    Text("Your Stats", style = MaterialTheme.typography.displaySmall)
                    HorizontalDivider()
                    Text("Your reading: ${readingBooks.size} books")
                    Text("Your read: ${readBookList.size} books")

                }
            }

            if(homeViewModel.data.value.loading == true){
                LinearProgressIndicator()
            } else {
                HorizontalDivider()
                LazyColumn(modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(readBookList){ book ->
                        ListBookCardRow2(book, navController)
                    }

                }
            }
        }

    }
}
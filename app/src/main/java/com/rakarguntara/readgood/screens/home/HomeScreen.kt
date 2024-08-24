package com.rakarguntara.readgood.screens.home

import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.rakarguntara.readgood.components.FABContent
import com.rakarguntara.readgood.components.LoadingIndicator
import com.rakarguntara.readgood.models.BookModel
import com.rakarguntara.readgood.navigation.ReadScreens
import com.rakarguntara.readgood.viewmodel.home.HomeViewModel
import com.rakarguntara.readgood.widgets.ListBookCard
import com.rakarguntara.readgood.widgets.ReadAppBar

@Composable
fun HomeScreen(
    navController: NavController = NavController(LocalContext.current),
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        ReadAppBar("Read Good", navController = navController)
    }, floatingActionButton = {
       FABContent{
           navController.navigate(ReadScreens.SearchScreen.name)
       }
    }) {
        Surface(modifier = Modifier.padding(it).fillMaxSize().padding(horizontal = 8.dp)) {
            HomeContent(navController, homeViewModel)
        }
    }
}

@Composable
fun HomeContent(navController: NavController, homeViewModel: HomeViewModel){
    var listOfBooks = emptyList<BookModel>()
    if(homeViewModel.data.value.loading == true){
        LoadingIndicator(true)
    } else {
        LoadingIndicator(false)
        listOfBooks = homeViewModel.data.value.data!!.toList().filter { book ->
            book.userId.toString() == FirebaseAuth.getInstance().currentUser?.uid.toString()
        }
    }

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
        ReadNow(books = listOfBooks, navController = navController)
        TitleSection(modifier = Modifier, label = "Reading List")
        BookListArea(listOfBooks = listOfBooks, navController = navController)
    }
}

@Composable
fun BookListArea(listOfBooks: List<BookModel>, navController: NavController) {
    val addedBooks = listOfBooks.filter { book ->
        book.startRead == null && book.finishedRead == null
    }
    HorizontalScrollableComponents(addedBooks){
        navController.navigate(ReadScreens.UpdateScreen.name + "/$it")
        Log.d("Dummy Data", "BookListArea: $it")
    }
}

@Composable
fun HorizontalScrollableComponents(listOfBooks: List<BookModel>, viewModel: HomeViewModel = hiltViewModel(), onCardPressed: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    Row(modifier = Modifier.fillMaxWidth().height(280.dp).horizontalScroll(scrollState)) {
        if(viewModel.data.value.loading == true){
          LinearProgressIndicator()
        } else if(listOfBooks.isNullOrEmpty()){
            Surface() {
                Text(text = "No books found, read book now!", color = Color.Blue,
                    fontSize = 14.sp)
            }
        } else {
            for(book in listOfBooks){
                ListBookCard(book){
                    onCardPressed(it)
                }
            }
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
    val readingNow = books.filter { book ->
        book.startRead != null && book.finishedRead == null
    }
    HorizontalScrollableComponents(readingNow){
        navController.navigate(ReadScreens.UpdateScreen.name + "/${it}")
    }
}



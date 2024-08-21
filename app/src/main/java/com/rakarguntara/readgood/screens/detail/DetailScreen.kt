package com.rakarguntara.readgood.screens.detail

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rakarguntara.readgood.components.LoadingIndicator
import com.rakarguntara.readgood.models.BookDetailModelResponse
import com.rakarguntara.readgood.models.BookModel
import com.rakarguntara.readgood.network.ResponseStateAlt
import com.rakarguntara.readgood.viewmodel.detail.DetailViewModel
import com.rakarguntara.readgood.widgets.ReadAppBar
import com.rakarguntara.readgood.widgets.RoundedButton

@Composable
fun DetailScreen(navController: NavController, bookId: String,
                 detailViewModel: DetailViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            ReadAppBar(title = "Detail", navController = navController,
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                showProfile = false

            ){
                navController.popBackStack()
            }
        }
    ) {
        Surface(modifier = Modifier.padding(it).padding(16.dp).fillMaxSize(),
            color = Color.White) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val bookInfo = produceState<ResponseStateAlt<BookDetailModelResponse>>(initialValue = ResponseStateAlt.Loading()){
                    value = detailViewModel.getBookDetail(bookId)
                }.value
                if(bookInfo.data == null){
                    LoadingIndicator(true)
                } else {
                    LoadingIndicator(false)
                    Text(bookInfo.data.volumeInfo?.title.toString(),
                        style = TextStyle(
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(bottom = 16.dp))
                    DetailMainContent(bookInfo.data, navController)
                }

            }
        }
    }
}

@Composable
fun DetailMainContent(data: BookDetailModelResponse, navController: NavController){

    Card(shape = CircleShape) {
        AsyncImage(model = data.volumeInfo?.imageLinks?.thumbnail,
            contentDescription = "Potret Buku",
            modifier = Modifier.size(150.dp))
    }
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(text = "Authors: ${data.volumeInfo?.authors}", color = Color.Black)
        Text(text = "Page Count: ${data.volumeInfo?.pageCount}", color = Color.Black)
        Text(text = "Published: ${
            if(data.volumeInfo?.publishedDate.toString().isNotEmpty()){
                data.volumeInfo?.publishedDate
            }  else {
                "Unknown"
            }
        }", color = Color.Black)
        Text(text = "Publisher: ${data.volumeInfo?.publisher}", color = Color.Black)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        RoundedButton(label = "Save") {
            val book = BookModel(
                id = data.id,
                title = data.volumeInfo?.title,
                author = data.volumeInfo?.authors.toString(),
                pageCount = data.volumeInfo?.pageCount.toString(),
                published = data.volumeInfo?.publishedDate,
                publisher = data.volumeInfo?.publisher
            )
            saveToFirebase(book)
        }
        Spacer(modifier = Modifier.padding(16.dp))
        RoundedButton(label = "Cancel") {
            navController.popBackStack()
        }
    }


}


fun saveToFirebase(book: BookModel) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books").document(FirebaseAuth.getInstance().currentUser!!.uid)
        .collection("added")
    if(book.id.toString().isNotEmpty()){
        dbCollection.add(book).addOnCompleteListener {
            Log.d("SAVE SUCCESS", "saveToFirebase: Book save success")
        }.addOnFailureListener {
            Log.d("SAVE FAILED", "saveToFirebase: Book save failed")
        }
    }
}

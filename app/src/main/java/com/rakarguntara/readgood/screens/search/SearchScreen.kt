package com.rakarguntara.readgood.screens.search

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rakarguntara.readgood.components.LoadingIndicator
import com.rakarguntara.readgood.components.SearchForm
import com.rakarguntara.readgood.viewmodel.search.SearchViewModel
import com.rakarguntara.readgood.widgets.ListBookCardRow
import com.rakarguntara.readgood.widgets.ReadAppBar

@Composable
fun SearchScreen(navController: NavController, searchViewModel: SearchViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        ReadAppBar(
            title = "Search Book",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            navController = navController, showProfile = false,
            onBackArrowClicked = {navController.popBackStack()})
    }) {
        Surface(modifier = Modifier.padding(it).fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                SearchForm(searchViewModel){ query ->
                    searchViewModel.searchBooks(query)

                }
                BookList(navController, searchViewModel)
            }

        }
    }
}

@Composable
fun BookList(navController: NavController, searchViewModel: SearchViewModel) {
    val listOfBook = searchViewModel.listOfBook.value.data
    if(searchViewModel.listOfBook.value.loading == true){
        LoadingIndicator(true)
    } else {
        LoadingIndicator(false)
        Log.d("SEARCH DATA", "BookList: $listOfBook")
        if(listOfBook != null){
            LazyColumn(
                modifier = Modifier.padding(16.dp)
                    .fillMaxWidth()
            ) {
                items(items = listOfBook){ book ->
                    ListBookCardRow(book, navController)
                }
            }
        } else {
            Toast.makeText(LocalContext.current, "Tidak ada buku", Toast.LENGTH_SHORT).show()
        }

    }

}


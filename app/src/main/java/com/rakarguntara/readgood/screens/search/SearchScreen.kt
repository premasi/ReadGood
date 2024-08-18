package com.rakarguntara.readgood.screens.search

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rakarguntara.readgood.components.LoadingIndicator
import com.rakarguntara.readgood.components.SearchForm
import com.rakarguntara.readgood.models.BookModel
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
    if(searchViewModel.listOfBook.value.loading == true){
        LoadingIndicator(true)
    } else {
        LoadingIndicator(false)
        Log.d("SEARCH DATA", "BookList: ${searchViewModel.listOfBook.value.data}")
    }

    val listOfBooks = listOf(
        BookModel(id = "1asd", title = "Test", author = "test", notes = null),
        BookModel(id = "1asd", title = "Test", author = "test", notes = null),
        BookModel(id = "1asd", title = "Test", author = "test", notes = null),
        BookModel(id = "1asd", title = "Test", author = "test", notes = null),
        BookModel(id = "1asd", title = "Test", author = "test", notes = null),
        BookModel(id = "1asd", title = "Test", author = "test", notes = null),
    )
    LazyColumn(
        modifier = Modifier.padding(16.dp)
            .fillMaxWidth()
    ) {
        items(items = listOfBooks){ book ->
            ListBookCardRow(book, navController)
        }
    }
}


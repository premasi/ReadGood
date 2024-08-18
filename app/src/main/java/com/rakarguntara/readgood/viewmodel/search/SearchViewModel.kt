package com.rakarguntara.readgood.viewmodel.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rakarguntara.readgood.models.ItemsItem
import com.rakarguntara.readgood.network.ResponseState
import com.rakarguntara.readgood.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val networkRepository: NetworkRepository) : ViewModel() {
    private val _listOfBook : MutableState<ResponseState<List<ItemsItem>, Boolean, Exception>> =
        mutableStateOf(ResponseState<List<ItemsItem>, Boolean, Exception>())
    val listOfBook get() = _listOfBook

    init {
        searchBooks("android")
    }

    fun searchBooks(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) return@launch

            _listOfBook.value = ResponseState(loading = true)

            val response = networkRepository.getBooksBySearch(query)

            response.data?.let { items ->
                _listOfBook.value = ResponseState(data = items, loading = false)
                Log.d("LIST BOOK", "Fetched Books: $items")
            }

            response.e?.let { e ->
                _listOfBook.value = ResponseState(e = e)
                Log.e("ERROR", "Error fetching books: $e")
            }
        }
    }
}
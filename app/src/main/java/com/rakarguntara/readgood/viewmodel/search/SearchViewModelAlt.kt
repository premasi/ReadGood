package com.rakarguntara.readgood.viewmodel.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rakarguntara.readgood.models.ItemsItem
import com.rakarguntara.readgood.network.ResponseStateAlt
import com.rakarguntara.readgood.repository.NetworkRepositoryAlt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModelAlt @Inject constructor(private val networkRepositoryAlt: NetworkRepositoryAlt): ViewModel() {
    private var list: List<ItemsItem> by mutableStateOf(listOf())

    init {
        loadBook("android")
    }

    fun loadBook(s: String) {
        viewModelScope.launch {
            if(s.isEmpty()){
                return@launch
            }
            try {
                when(val response = networkRepositoryAlt.getBookBySearch(s)){
                    is ResponseStateAlt.Error -> Log.d("ERROR ALTERNATIVE", "loadBook: ")
                    is ResponseStateAlt.Loading -> {}
                    is ResponseStateAlt.Success -> list = response.data!!
                }
            } catch (e: Exception){
                Log.d("ERROR ALTERNATIVE EX", "loadBook: ")
            }
        }
    }
}
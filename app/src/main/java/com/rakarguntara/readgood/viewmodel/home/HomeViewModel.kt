package com.rakarguntara.readgood.viewmodel.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rakarguntara.readgood.models.BookModel
import com.rakarguntara.readgood.network.ResponseState
import com.rakarguntara.readgood.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val fireRepository: FirebaseRepository) : ViewModel() {
    private val _data: MutableState<ResponseState<List<BookModel>, Boolean, Exception>>
    = mutableStateOf(
        ResponseState(listOf(), true, Exception(""))
    )
    val data get() = _data

    init {
        getAllBookAdded()
    }

    private fun getAllBookAdded() {
        viewModelScope.launch {
            _data.value = fireRepository.getAllAddedBook()
            Log.d("GET DATA", "getAllBookAdded: ${_data.value.data?.toList()}")
        }

    }
}
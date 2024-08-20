package com.rakarguntara.readgood.viewmodel.detail

import androidx.lifecycle.ViewModel
import com.rakarguntara.readgood.models.BookDetailModelResponse
import com.rakarguntara.readgood.network.ResponseStateAlt
import com.rakarguntara.readgood.repository.NetworkRepositoryAlt
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: NetworkRepositoryAlt): ViewModel() {
    suspend fun getBookDetail(id: String): ResponseStateAlt<BookDetailModelResponse>{
        return repository.getBookDetail(id)
    }
}
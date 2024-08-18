package com.rakarguntara.readgood.repository

import com.rakarguntara.readgood.models.BookDetailModelResponse
import com.rakarguntara.readgood.models.ItemsItem
import com.rakarguntara.readgood.network.ApiService
import com.rakarguntara.readgood.network.ResponseStateAlt
import javax.inject.Inject


class NetworkRepositoryAlt @Inject constructor(private val apiService: ApiService) {
    suspend fun getBookBySearch(query: String): ResponseStateAlt<List<ItemsItem>>{
        return try {
            ResponseStateAlt.Loading(true)
            val response = apiService.getAllBookByQuery(query).items
            ResponseStateAlt.Success(data = response)
        } catch (e: Exception){
            ResponseStateAlt.Error(message = e.message.toString())
        }

    }

    suspend fun getBookDetail(id: String): ResponseStateAlt<BookDetailModelResponse>{
        return try {
            ResponseStateAlt.Loading(true)
            val response = apiService.getBookInfoById(id)
            ResponseStateAlt.Success(response)
        }catch (e: Exception){
            ResponseStateAlt.Error(message = e.message.toString())
        }
    }
}
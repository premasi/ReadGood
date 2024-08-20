package com.rakarguntara.readgood.repository

import com.rakarguntara.readgood.models.ItemsItem
import com.rakarguntara.readgood.network.ApiService
import com.rakarguntara.readgood.network.ResponseState
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val apiService: ApiService){

    suspend fun getBooksBySearch(query: String): ResponseState<
            List<ItemsItem>,
            Boolean,
            Exception> {
        val response = try {
            apiService.getAllBookByQuery(query).items
        } catch (e: Exception){
            return ResponseState(e = e)
        }
        return ResponseState(data = response)
    }

//    suspend fun getBookInfoById (id: String): ResponseState<BookDetailModelResponse,
//            Boolean, Exception> {
//        val response = try {
//            apiService.getBookInfoById(id)
//        } catch (e: Exception){
//            return ResponseState(e = e)
//        }
//        return ResponseState(data = response)
//    }
}
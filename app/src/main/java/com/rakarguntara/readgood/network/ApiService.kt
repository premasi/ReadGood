package com.rakarguntara.readgood.network

import com.rakarguntara.readgood.models.BookDetailModelResponse
import com.rakarguntara.readgood.models.BookModelResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface ApiService {
    @GET("/volumes")
    suspend fun getAllBookByQuery(
        @Query("q") query: String
    ) : BookModelResponse

    @GET("/volumes/{bookId}")
    suspend fun getBookInfoById(
        @Path("bookId") id: String
    ): BookDetailModelResponse
}
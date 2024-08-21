package com.rakarguntara.readgood.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.rakarguntara.readgood.models.BookModel
import com.rakarguntara.readgood.network.ResponseState
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val queryBook: Query
) {
    suspend fun getAllAddedBook(): ResponseState<List<BookModel>, Boolean, Exception>{
        val responseState = ResponseState<List<BookModel>, Boolean, Exception>()
        try {
            responseState.loading = true
            responseState.data = queryBook.get().await().documents.map { docSnap ->
                docSnap.toObject(BookModel::class.java)!!
            }
            Log.d("FIRE REPO", "getAllAddedBook: ${responseState.data}")
            if(!responseState.data.isNullOrEmpty()) responseState.loading = false
        }catch (e: FirebaseFirestoreException){
            responseState.e = e
        }
        return responseState
    }
}
package com.rakarguntara.readgood.models

import com.google.firebase.Timestamp

data class BookModel(
    var id: String? = null,
    var title: String? = null,
    var author: String? = null,
    var pageCount: String? = null,
    var published: String? = null,
    var publisher: String? = null,
    var startRead: Timestamp? = null,
    var finishedRead: Timestamp? = null,
    var userId : String? = null
)

package com.rakarguntara.readgood.utils

import com.google.firebase.Timestamp
import java.text.DateFormat

fun formatDate(timestamp: Timestamp): String {
    val date = DateFormat.getDateInstance()
        .format(timestamp.toDate())
        .toString()

    return date
}
package com.rakarguntara.readgood.network

data class ResponseState<T, Boolean, E: Exception>(
    var data: T? = null,
    var loading: Boolean? = null,
    var e: E? = null
)
package com.rakarguntara.readgood.network

sealed class ResponseStateAlt<T>(val data: T? =  null, val message: String? = null) {
    class Success<T>(data: T?): ResponseStateAlt<T>(data)
    class Error<T>(message: String?, data: T? = null): ResponseStateAlt<T>(data, message)
    class Loading<T>(data: T? = null): ResponseStateAlt<T>(data)
}
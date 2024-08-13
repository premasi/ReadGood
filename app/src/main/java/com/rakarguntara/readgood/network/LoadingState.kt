package com.rakarguntara.readgood.network

data class LoadingState(val status: Status, val message: String? = null){
    enum class Status{
        LOADING,
        SUCCESS,
        IDLE,
        FAILED
    }

    companion object{
        val IDLE = LoadingState(Status.IDLE)
        val SUCCESS = LoadingState(Status.SUCCESS)
        val failed = LoadingState(Status.FAILED)
        val loading = LoadingState(Status.LOADING)
    }
}
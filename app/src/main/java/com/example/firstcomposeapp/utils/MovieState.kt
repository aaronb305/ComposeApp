package com.example.firstcomposeapp.utils

sealed class MovieState {
    object LOADING : MovieState()
    class SUCCESS<T>(val response : T) : MovieState()
    class ERROR(val error : Throwable) : MovieState()
}

package com.example.firstcomposeapp.utils

import android.util.Log
import retrofit2.Response
import java.lang.Exception

inline fun <In> responseTryCatch(
    response : () -> Response<In>,
    onSuccess : (MovieState.SUCCESS<In>) -> Unit,
    onError : (MovieState.ERROR) -> Unit
)  {
    Log.d("utils", "entered response try catch")
    try {
        val result = response()
        Log.d("utils", "entered response try block")
        if (result.isSuccessful) {
            Log.d("utils", "entered response success")
            result.body()?.let {
                onSuccess(MovieState.SUCCESS(it))
            } ?: throw Exception("Response is null")
        }
        else {
            Log.d("utils", "entered response failed")
            throw Exception("Unsuccessful response")
        }
    }
    catch (e: Exception) {
        Log.d("utils", "entered response catch block")
        Log.e("utils", e.localizedMessage)
        onError(MovieState.ERROR(e))
    }
}
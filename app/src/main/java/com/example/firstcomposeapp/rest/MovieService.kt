package com.example.firstcomposeapp.rest

import com.example.firstcomposeapp.model.MoviesItem
import retrofit2.Response
import retrofit2.http.GET

interface MovieService {

    @GET(MOVIE_ENDPOINT)
    suspend fun getMovies() : Response<List<MoviesItem>>

    companion object {
        const val BASE_URL = "https://howtodoandroid.com/apis/"
        private const val MOVIE_ENDPOINT = "movielist.json"
    }
}
package com.example.firstcomposeapp.rest

import com.example.firstcomposeapp.utils.MovieState
import com.example.firstcomposeapp.utils.responseTryCatch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl(
    private val movieService: MovieService
) : MovieRepository {

    override fun getMovies(): Flow<MovieState> =
        flow {
            responseTryCatch(
                { movieService.getMovies() },
                { emit(it) },
                { emit(it) }
            )
        }
}

interface MovieRepository {
    fun getMovies() : Flow<MovieState>
}
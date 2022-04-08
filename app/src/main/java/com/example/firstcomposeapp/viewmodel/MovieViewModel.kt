package com.example.firstcomposeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstcomposeapp.rest.MovieRepository
import com.example.firstcomposeapp.rest.MovieService
import com.example.firstcomposeapp.utils.MovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _moviesData : MutableLiveData<MovieState> = MutableLiveData(MovieState.LOADING)
    val moviesData : LiveData<MovieState> get() = _moviesData

    fun getMovies() {
        viewModelScope.launch(dispatcher) {
            movieRepository.getMovies().collect {
                _moviesData.postValue(it)
            }
        }
    }
}
package com.example.firstcomposeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstcomposeapp.rest.MovieRepository
import com.example.firstcomposeapp.utils.MovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movies: MutableLiveData<MovieState> = MutableLiveData(MovieState.LOADING)
    val movies: LiveData<MovieState> get() = _movies

    fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getMovies().collect {
                _movies.postValue(it)
            }
        }
    }
}
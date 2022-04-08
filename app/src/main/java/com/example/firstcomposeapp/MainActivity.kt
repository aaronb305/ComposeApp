package com.example.firstcomposeapp

import android.graphics.ColorMatrix
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.firstcomposeapp.model.MoviesItem
import com.example.firstcomposeapp.ui.theme.FirstComposeAppTheme
import com.example.firstcomposeapp.utils.MovieState
import com.example.firstcomposeapp.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MovieViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FirstComposeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Recycler(state = viewModel.moviesData.observeAsState().value)
                    viewModel.getMovies()
                }
            }
        }
    }
}

private val mList = (0..100).toList()

@OptIn(ExperimentalCoilApi::class)
@Composable
fun MovieItem(moviesItem: MoviesItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp
    ) {
        Surface() {
            Row() {
               Image(painter = rememberImagePainter(
                   data = moviesItem.imageUrl,
               builder = {
                   scale(Scale.FILL)
                   transformations(CircleCropTransformation())
               }), contentDescription = moviesItem.desc,
                   modifier = Modifier
                       .fillMaxHeight()
                       .weight(0.2f))
               Column {
                   Text(text = moviesItem.name)
                   Text(text = moviesItem.category)
                   Text(
                       text = moviesItem.desc,
                       maxLines = 3,
                       overflow = TextOverflow.Ellipsis
                   )
               }
            }
        }
    }
}

@Composable
fun Movies(movies : List<MoviesItem>) {
    LazyColumn(
        modifier = Modifier.padding(5.dp)
    ) {
        items(movies) {
            MovieItem(it)
        }
    }
}

@Composable
fun Recycler(state : MovieState?) {
    when (val myState = state ?: MovieState.ERROR(Throwable("Null response"))) {
        is MovieState.LOADING -> {  }
        is MovieState.SUCCESS<*> -> {
            val movies = myState.response as List<MoviesItem>
            Movies(movies = movies)
        }
        is MovieState.ERROR -> {  }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FirstComposeAppTheme {
        Column(
            modifier = Modifier
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        }
    }
}
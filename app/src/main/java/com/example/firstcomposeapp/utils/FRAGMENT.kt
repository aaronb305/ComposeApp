package com.example.firstcomposeapp.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.firstcomposeapp.model.MoviesItem

@Composable
fun MoviesRecycler(state: MovieState?) {
    when(val myState = state ?: MovieState.ERROR(Throwable("ERROR"))) {
        is MovieState.LOADING -> {}
        is MovieState.SUCCESS<*> -> {
            Movies(movies = myState.response as List<MoviesItem>)
        }
        is MovieState.ERROR -> {}
        else -> {}
    }
}

@Composable
fun Movies(movies: List<MoviesItem>) {
    LazyColumn(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxSize()
    ) {
        items(movies) {
            Movie(item = it)
        }
    }
}

@Composable
fun Movie(item: MoviesItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 5.dp
    ) {
        Surface {
            Row(
                modifier = Modifier.padding(5.dp)
            ) {
                Column() {
                    Text(text = "Name: ${item.name}")
                    Text(
                        text = "CAT: ${item.category}",
                        modifier = Modifier.padding(5.dp),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "DESC: ${item.desc}",
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
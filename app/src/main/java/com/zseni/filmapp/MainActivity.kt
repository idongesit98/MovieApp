package com.zseni.filmapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.zseni.filmapp.presentation.navigation.MovieNavigation
import com.zseni.filmapp.presentation.screens.movieHome.HomeScreen
import com.zseni.filmapp.presentation.screens.popularMovies.PopularMovieScreen
import com.zseni.filmapp.presentation.viewModels.MovieListViewModel
import com.zseni.filmapp.ui.theme.MovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val movieListViewModel = hiltViewModel<MovieListViewModel>()
                    val movieState = movieListViewModel.movieListState.collectAsState().value
                    MovieNavigation(
                        onEvent = movieListViewModel::onEvent,
                        mainMovieState = movieState
                        )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MovieAppTheme {
    }
}

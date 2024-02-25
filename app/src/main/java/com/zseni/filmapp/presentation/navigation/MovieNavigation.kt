package com.zseni.filmapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zseni.filmapp.presentation.screens.mainUiScreen.MainMovieState
import com.zseni.filmapp.presentation.screens.mainUiScreen.MainUiEvent
import com.zseni.filmapp.presentation.screens.movieDetails.DetailScreen
import com.zseni.filmapp.presentation.screens.mainUiScreen.MovieMainScreen
import com.zseni.filmapp.presentation.screens.movieDetails.TvDetailScreen
import com.zseni.filmapp.presentation.screens.popularMovies.PopularMovieScreen
import com.zseni.filmapp.presentation.screens.searchScreen.SearchScreen
import com.zseni.filmapp.presentation.screens.upcomingMovies.RecommendedMoviesScreen
import com.zseni.filmapp.presentation.screens.upcomingMovies.TopRatedScreen
import com.zseni.filmapp.presentation.screens.upcomingMovies.TrendingMoviesScreen
import com.zseni.filmapp.presentation.screens.upcomingMovies.TvSeriesScreen
import com.zseni.filmapp.presentation.screens.upcomingMovies.UpcomingMovieScreen
import com.zseni.filmapp.util.Screen

@Composable
fun MovieNavigation(
    onEvent:(MainUiEvent) -> Unit,
    mainMovieState: MainMovieState
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.MainUiScreen.route){

        composable(Screen.MainUiScreen.route){
            MovieMainScreen(navController,mainMovieState, onEvent)


        }
        composable(Screen.DetailsScreen.route + "/{movieId}",
            arguments = listOf(
                navArgument("movieId"){type = NavType.IntType}
            )
        ){
            DetailScreen()
        }
        composable(Screen.TvDetailsScreen.route + "/{tvId}",
            arguments = listOf(
                navArgument("tvId"){type = NavType.IntType}
            )
        ){
            TvDetailScreen()
        }
        composable(Screen.PopularMovieScreen.route){
            PopularMovieScreen(mainMovieState = mainMovieState, navController = navController, onEvent = onEvent)
        }

        composable(Screen.UpcomingMovieScreen.route){
            UpcomingMovieScreen(mainMovieState = mainMovieState, navController = navController, onEvent = onEvent)
        }
        composable(Screen.TrendingMovies.route){
            TrendingMoviesScreen(mainMovieState = mainMovieState, navController = navController, onEvent = onEvent)
        }
        composable(Screen.TvSeriesScreen.route){
            TvSeriesScreen(
                navController = navController,
                mainMovieState = mainMovieState
            )
        }
        composable(Screen.SearchScreen.route){
            SearchScreen(navController = navController, mainMovieState = mainMovieState)
        }
        composable(Screen.RecommendedScreen.route){
            RecommendedMoviesScreen(navController = navController, mainMovieState = mainMovieState, onEvent = onEvent)
        }
        composable(Screen.TopRatedScreen.route){
            TopRatedScreen(
                navController = navController,
                mainMovieState = mainMovieState,
                onEvent = onEvent
            )
        }
    }
}
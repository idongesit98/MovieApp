package com.zseni.filmapp.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zseni.filmapp.R
import com.zseni.filmapp.domain.model.Movie
import com.zseni.filmapp.presentation.screens.mainUiScreen.MainMovieState
import com.zseni.filmapp.ui.theme.adaminaFont
import com.zseni.filmapp.util.Screen

//TODO: MODIFY TO AUTO SWIPE SECTION AND THEN CALL IT THE SAME WAY AUTO SWIPE IS BEING CALLED
@Composable
fun TrendingMoviesSection(
    movieTitle:String,
    showShimmer:Boolean,
    navController: NavController,
    mainUiState:MainMovieState,
    clickable: () -> Unit,
){
    if (showShimmer){
        ShowHomeShimmer(
            title = movieTitle,
            modifier = Modifier
                .height(220.dp)
                .width(150.dp)
                .padding(
                    top = 20.dp,
                    start = 16.dp, bottom = 12.dp
                ),
            paddingEnd = 16.dp
        )
    }else{
        SlideMovies(
            movieTitle = movieTitle,
            movieList = mainUiState.nowPlayingMoviesList,
            navController = navController,
            clickable = {clickable.invoke()},
            mainUiState = mainUiState
        )
    }
}
@Composable
fun UpcomingMoviesSection(
    movieTitle:String,
    showShimmer:Boolean,
    navController: NavController,
    mainUiState:MainMovieState,
    clickable: () -> Unit,
){
    if (showShimmer){
        ShowHomeShimmer(
            title = movieTitle,
            modifier = Modifier
                .height(220.dp)
                .width(150.dp)
                .padding(
                    top = 20.dp,
                    start = 16.dp, bottom = 12.dp
                ),
            paddingEnd = 16.dp
        )
    }else{
        SlideMovies(
            movieTitle = movieTitle,
            movieList = mainUiState.upcomingMovieList,
            navController = navController,
            clickable = {clickable.invoke()},
            mainUiState = mainUiState
        )
    }
}
@Composable
fun TvSeriesSection(
    movieTitle:String,
    showShimmer:Boolean,
    clickable: () -> Unit,
    mainUiState:MainMovieState
){
    val navController = rememberNavController()
    if (showShimmer){
        ShowHomeShimmer(
            title = movieTitle,
            modifier = Modifier
                .height(220.dp)
                .width(150.dp)
                .padding(
                    top = 20.dp,
                    start = 16.dp, bottom = 12.dp
                ),
            paddingEnd = 16.dp
        )
    }else{
        SlideMovies(
            movieTitle = movieTitle,
            movieList = mainUiState.topRatedTvSeriesList,
            navController = navController,
            clickable = {clickable.invoke()},
            mainUiState = mainUiState
        )
    }
}

@Composable
fun TopRatedMoviesSection(
    movieTitle:String,
    showShimmer:Boolean,
    clickable: () -> Unit,
    mainUiState:MainMovieState
){
    val navController = rememberNavController()
    if (showShimmer){
        ShowHomeShimmer(
            title = movieTitle,
            modifier = Modifier
                .height(220.dp)
                .width(150.dp)
                .padding(
                    top = 20.dp,
                    start = 16.dp, bottom = 12.dp
                ),
            paddingEnd = 16.dp
        )
    }else{
        SlideMovies(
            movieTitle = movieTitle,
            movieList = mainUiState.topRatedTvMovieList,
            navController = navController,
            clickable = {clickable.invoke()},
            mainUiState = mainUiState
        )
    }
}
@Composable
fun RecommendedMoviesSection(
    movieTitle:String,
    showShimmer:Boolean,
   clickable: () -> Unit,
    mainUiState:MainMovieState
){
    val navController = rememberNavController()
    if (showShimmer){
        ShowHomeShimmer(
            title = movieTitle,
            modifier = Modifier
                .height(220.dp)
                .width(150.dp)
                .padding(
                    top = 20.dp,
                    start = 16.dp, bottom = 12.dp
                ),
            paddingEnd = 16.dp
        )
    }else{
        SlideMovies(
            movieTitle = movieTitle,
            movieList = mainUiState.nowPlayingMoviesList,
            navController = navController,
            clickable = {clickable.invoke()},
            mainUiState = mainUiState
        )
    }
}
@Composable
fun AiringMoviesSlideSection(
    movieTitle:String,
    showShimmer:Boolean,
    clickable: () -> Unit,
    mainUiState:MainMovieState
){
    val navController = rememberNavController()
    if (showShimmer){
        ShowHomeShimmer(
            title = movieTitle,
            modifier = Modifier
                .height(220.dp)
                .width(150.dp)
                .padding(
                    top = 20.dp,
                    start = 16.dp, bottom = 12.dp
                ),
            paddingEnd = 16.dp
        )
    }else{
        SlideMovies(
            movieTitle = movieTitle,
            movieList = mainUiState.airingTodayTvSeriesList,
            navController = navController,
            clickable = {clickable.invoke()},
            mainUiState = mainUiState
        )
    }
}
@Composable
fun UpcomingMovieSlideSection(
    movieTitle:String,
    showShimmer:Boolean,
    mainUiState:MainMovieState,
    clickable: () -> Unit
){
    val navController = rememberNavController()
    if (showShimmer){
        ShowHomeShimmer(
            title = movieTitle,
            modifier = Modifier
                .height(220.dp)
                .width(150.dp)
                .padding(
                    top = 20.dp,
                    start = 16.dp, bottom = 12.dp
                ),
            paddingEnd = 16.dp
        )
    }else{
        SlideMovies(
            movieTitle = movieTitle,
            movieList = mainUiState.upcomingMovieList,
            navController = navController,
            clickable = {clickable.invoke()},
            mainUiState = mainUiState
        )
    }
}
@Composable
fun TopRatedSeriesSection(
    movieTitle:String,
    showShimmer:Boolean,
    clickable: () -> Unit,
    mainUiState:MainMovieState
){
    val navController = rememberNavController()
    if (showShimmer){
        ShowHomeShimmer(
            title = movieTitle,
            modifier = Modifier
                .height(220.dp)
                .width(150.dp)
                .padding(
                    top = 20.dp,
                    start = 16.dp, bottom = 12.dp
                ),
            paddingEnd = 16.dp
        )
    }else{
        SlideMovies(
            movieTitle = movieTitle,
            movieList = mainUiState.topRatedTvSeriesList,
            navController = navController,
            clickable = {clickable.invoke()},
            mainUiState = mainUiState
        )
    }
}
@Composable
fun PopularTvSeriesSection(
    movieTitle:String,
    showShimmer:Boolean,
    clickable: () -> Unit,
    mainUiState:MainMovieState
){
    val navController = rememberNavController()
    if (showShimmer){
        ShowHomeShimmer(
            title = movieTitle,
            modifier = Modifier
                .height(220.dp)
                .width(150.dp)
                .padding(
                    top = 20.dp,
                    start = 16.dp, bottom = 12.dp
                ),
            paddingEnd = 16.dp
        )
    }else{
        SlideMovies(
            movieTitle = movieTitle,
            movieList = mainUiState.popularTvSeriesList,
            navController = navController,
            clickable = {clickable.invoke()},
            mainUiState = mainUiState
        )
    }
}

@Composable
fun SlideMovies(
    movieTitle:String,
    movieList: List<Movie>,
    navController: NavController,
    clickable:()->Unit,
    mainUiState: MainMovieState
){
    Column {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),//32
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = movieTitle,
                color = MaterialTheme.colorScheme.onBackground,
                fontFamily = adaminaFont,
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier
                    .alpha(0.7f)
                    .clickable { clickable.invoke() },
                text = stringResource(id = R.string.see_all),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontFamily = adaminaFont,
                fontSize = 14.sp
            )
        }
        LazyRow{
            items(movieList.size){index ->
                var paddingEnd = 0.dp
                if (index == movieList.size-1){
                    paddingEnd = 16.dp
                }
                MoviesItem(
                    movie = movieList[index],
                    navController = navController,
                    mainMovieState = mainUiState,
                    modifier = Modifier
                        .height(200.dp)
                        .width(150.dp)
                        .padding(start = 16.dp,end = paddingEnd)
                )

            }

        }
    }
}
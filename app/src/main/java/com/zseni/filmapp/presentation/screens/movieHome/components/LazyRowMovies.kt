package com.zseni.filmapp.presentation.screens.movieHome.components

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
import androidx.navigation.NavHostController
import com.zseni.filmapp.presentation.screens.mainUiScreen.MainMovieState
import com.zseni.filmapp.R
import com.zseni.filmapp.presentation.component.ItemInMovies
import com.zseni.filmapp.ui.theme.adaminaFont
import com.zseni.filmapp.util.Screen

//This composable handles the movies placed in a horizontal position in each screen
@Composable
fun LazyRowMovies(
    type:String,
    navController: NavController,
    bottomBarNavController:NavHostController,
    mainMovieState: MainMovieState
){
    val movieTitle = when(type){
       "latestMovies" -> {
           stringResource(id = R.string.latest)
        }
        "trendingMovies" ->{
            stringResource(id = R.string.trending_movies)
        }
        "tvSeries" ->{
          stringResource(id = R.string.tvSeries)
        }

        "upComingMovies" ->{
            stringResource(id = R.string.upcoming)
        }
        else ->{
            stringResource(id = R.string.top_rated)
        }
    }

    val movieLists = when(type){
        "latestMovies" ->{
            mainMovieState.nowPlayingMoviesList.take(10)
        }
        "trendingMovies" ->{
            mainMovieState.trendingMoviesList.take(10)
        }
        "tvSeries" ->{
            mainMovieState.topRatedTvSeriesList.take(10)
        }
        "upComingMovies"->{
            mainMovieState.upcomingMovieList.take(10)
        }
        else->{
            mainMovieState.popularTvSeriesList.take(10)
        }
    }

    Column {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp),
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
                    .clickable { bottomBarNavController.navigate(Screen.DetailsScreen.route) },
                text = stringResource(id = R.string.see_all),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontFamily = adaminaFont,
                fontSize = 14.sp
            )
        }
        LazyRow{
            items(movieLists.size){
                var paddingEnd = 0.dp
                if (it == movieLists.size -1){
                    paddingEnd = 16.dp
                }
               ItemInMovies(
                   movie = movieLists[it],
                   navController = navController,
                   modifier = Modifier
                       .height(200.dp)
                       .width(150.dp)
                       .padding(start = 16.dp,end = paddingEnd)
               )
            }
        }

    }
}


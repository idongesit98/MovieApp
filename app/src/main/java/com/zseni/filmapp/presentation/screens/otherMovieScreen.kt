package com.zseni.filmapp.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.zseni.filmapp.R
import com.zseni.filmapp.presentation.component.AllMovieItem
import com.zseni.filmapp.presentation.screens.mainUiScreen.MainMovieState
import com.zseni.filmapp.presentation.screens.mainUiScreen.MainUiEvent
import com.zseni.filmapp.presentation.screens.searchScreen.components.NonSearchScreenBar
import com.zseni.filmapp.ui.theme.adaminaFont
import com.zseni.filmapp.util.MovieListShimmer
import com.zseni.filmapp.util.Screen
import com.zseni.filmapp.util.header
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

//This serves as guide to other screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtherMovieScreen(
    selectedItem:MutableState<Int>,
    navController: NavController,
    bottomBarNavController:NavHostController,
    navBackStackEntry: NavBackStackEntry,
    mainMovieState: MainMovieState,
    onEvent:(MainUiEvent) -> Unit
) {
    val toolBarHeightPx = with(LocalDensity.current){74.dp.roundToPx().toFloat()}
    val toolbarOffsetHeightPx = remember{ mutableFloatStateOf(0f) }
    val nestedScrollConnection = remember{
        object :NestedScrollConnection{
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.floatValue + delta
                toolbarOffsetHeightPx.floatValue = newOffset.coerceIn(-toolBarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }
    BackHandler(enabled = true) {
       selectedItem.value = 0
       bottomBarNavController.navigate(Screen.HomeScreen.route)
    }
    val movieType = remember {
        navBackStackEntry.arguments?.getString("movieType")
    }
    val movieList = when(movieType){
        "latestMoviesScreen" -> mainMovieState.latestMoviesList
        "trendingMoviesScreen"-> mainMovieState.trendingMoviesList
        "topRatedMoviesScreen" -> mainMovieState.topRatedTvSeriesList
        "recommendedMoviesScreen" -> mainMovieState.recommendedMoviesList
        "tvSeriesMoviesScreen" -> mainMovieState.tvSeriesList
        else -> mainMovieState.popularMoviesList
    }

    val movieTitle = when(movieType){
         "latestMovies"-> stringResource(id = R.string.latest)
        "trendingMoviesScreen" -> stringResource(id = R.string.trending_movies)
        "topRatedMoviesScreen" -> stringResource(id = R.string.top_rated)
        "recommendedMoviesScreen" -> stringResource(id = R.string.recommended)
        "tvSeriesMoviesScreen" -> stringResource(id = R.string.tvSeries)
        else -> ""
    }

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember{ mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(1500)

        if (movieType != null){
            onEvent(MainUiEvent.Refresh(category = movieType))
        }
        refreshing = false
    }

    val refreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = { refresh()})

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.surface)
        .nestedScroll(nestedScrollConnection)
        .pullRefresh(refreshState)
    ){
        if (movieList.isEmpty()){
            MovieListShimmer(
                title = movieTitle,
                radius = 24
            )
        }else{
            val listState = rememberLazyGridState()
            LazyVerticalGrid(
                state = listState,
                columns = GridCells.Adaptive(190.dp),
                content = {
                    header {
                        Text(
                            modifier = Modifier
                                .padding(vertical = 16.dp, horizontal = 32.dp),
                            text = movieTitle,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontFamily = adaminaFont,
                            fontSize = 20.sp
                        )
                    }
                    items(movieList.size){i ->
                        AllMovieItem(
                            movie = movieList[i],
                            navController = navController,
                            mainMovieState = mainMovieState,
                            onEvent = onEvent
                        )
                        if (i>= movieList.size -1 && !mainMovieState.isLoading){
                            if (movieType!=null){
                                onEvent(MainUiEvent.Paginate(category = movieType))
                            }
                        }
                    }
                }
            )
            PullRefreshIndicator(
                refreshing = refreshing ,
                state = refreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 66.dp)
            )
           NonSearchScreenBar(
               toolbarOffsetHeightPx = toolbarOffsetHeightPx.floatValue.roundToInt(),
               navController = navController,
               //clickable = {navController.navigate(Screen.SearchScreen.route)}
           )
        }

    }

}
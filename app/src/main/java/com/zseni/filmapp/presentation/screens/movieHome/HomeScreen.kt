package com.zseni.filmapp.presentation.screens.movieHome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zseni.filmapp.R
import com.zseni.filmapp.presentation.component.AutoSwipeSection
import com.zseni.filmapp.presentation.component.RecommendedMoviesSection
import com.zseni.filmapp.presentation.component.TopRatedMoviesSection
import com.zseni.filmapp.presentation.component.TrendingMoviesSection
import com.zseni.filmapp.presentation.component.TvSeriesSection
import com.zseni.filmapp.presentation.component.UpcomingMoviesSection
import com.zseni.filmapp.presentation.screens.mainUiScreen.MainMovieState
import com.zseni.filmapp.presentation.screens.mainUiScreen.MainUiEvent
import com.zseni.filmapp.presentation.screens.searchScreen.components.NonSearchScreenBar
import com.zseni.filmapp.ui.theme.adaminaFont
import com.zseni.filmapp.util.Screen
import com.zseni.filmapp.util.shimmerEffect
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

//TODO:Start from here and create the home screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    mainMovieState: MainMovieState,
    onEvent:(MainUiEvent) -> Unit
){
    val toolbarHeightPx = with(LocalDensity.current){10.dp.roundToPx().toFloat()}
    val toolbarOffsetHeightPx = remember{ mutableFloatStateOf(0f) }
    val nestedScrollConnection = remember{
        object :NestedScrollConnection{
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.floatValue + delta
                toolbarOffsetHeightPx.floatValue = newOffset.coerceIn(-toolbarHeightPx,0f)
                return Offset.Zero
            }
        }
    }
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember{ mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(1500)
        onEvent(MainUiEvent.Refresh(category = "homeScreen"))
        refreshing = false
    }
    val refreshState = rememberPullRefreshState(
        refreshing = refreshing,::refresh
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 10.dp)
    ) {
        NonSearchScreenBar(
            toolbarOffsetHeightPx = toolbarOffsetHeightPx.floatValue.roundToInt(),
            navController = navController
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
                .pullRefresh(refreshState),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                TrendingMoviesSection(
                    movieTitle = stringResource(id = R.string.trending_movies),
                    showShimmer = mainMovieState.isLoading,
                    mainUiState = mainMovieState,
                    navController = navController,
                    clickable = {
                        navController.navigate(Screen.TrendingMovies.route)
                    }
                )
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                if (mainMovieState.specialList.isEmpty()) {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = stringResource(id = R.string.special),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = adaminaFont,
                        fontSize = 20.sp
                    )
                    Box(
                        modifier = Modifier
                            .height(220.dp)
                            .fillMaxWidth(0.9f)
                            .padding(top = 20.dp, bottom = 12.dp)
                            .clip(RoundedCornerShape(16))
                            .shimmerEffect(false)
                            .align(Alignment.CenterHorizontally)
                    )
                } else {
                    AutoSwipeSection(
                        type = stringResource(id = R.string.special),
                        navController = navController,
                        mainUiState = mainMovieState
                    )
                }
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                TvSeriesSection(
                    movieTitle = stringResource(id = R.string.tvSeries),
                    showShimmer = mainMovieState.isLoading,
                    mainUiState = mainMovieState,
                    clickable = { navController.navigate(Screen.TvSeriesScreen.route) }

                )
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                TopRatedMoviesSection(
                    movieTitle = stringResource(id = R.string.top_rated),
                    showShimmer = mainMovieState.isLoading,
                    mainUiState = mainMovieState,
                    clickable = { navController.navigate(Screen.TopRatedScreen.route) }
                )
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                RecommendedMoviesSection(
                    movieTitle = stringResource(id = R.string.recommended),
                    showShimmer = mainMovieState.isLoading,
                    mainUiState = mainMovieState,
                    clickable = { navController.navigate(Screen.RecommendedScreen.route) }
                )
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                UpcomingMoviesSection(
                    movieTitle = stringResource(id = R.string.upcoming),
                    showShimmer = mainMovieState.isLoading,
                    mainUiState = mainMovieState,
                    navController = navController,
                    clickable = {
                        navController.navigate(Screen.UpcomingMovieScreen.route)
                    }
                )
            }
            PullRefreshIndicator(
                refreshing = refreshing,
                state = refreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 60.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen(){
    val navController = rememberNavController()
    HomeScreen(
        navController = navController,
        mainMovieState = MainMovieState(
            isLoading = false,
            popularMoviesList = emptyList()
        ),
        onEvent = {}
    )
}
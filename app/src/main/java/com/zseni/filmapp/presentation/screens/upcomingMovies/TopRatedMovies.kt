package com.zseni.filmapp.presentation.screens.upcomingMovies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.zseni.filmapp.R
import com.zseni.filmapp.presentation.component.PopularTvSeriesSection
import com.zseni.filmapp.presentation.component.TopRatedSeriesSection
import com.zseni.filmapp.presentation.component.TvSeriesSection
import com.zseni.filmapp.presentation.screens.mainUiScreen.MainMovieState
import com.zseni.filmapp.presentation.screens.mainUiScreen.MainUiEvent
import com.zseni.filmapp.presentation.screens.searchScreen.components.NonSearchScreenBar
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopRatedScreen(
    navController: NavController,
    mainMovieState: MainMovieState,
    onEvent:(MainUiEvent) -> Unit
) {
    val toolBarHeightPx = with(LocalDensity.current){24.dp.roundToPx().toFloat()}
    val toolBarOffsetHeightPx = remember{ mutableFloatStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolBarOffsetHeightPx.floatValue + delta
                toolBarOffsetHeightPx.floatValue = newOffset.coerceIn(-toolBarHeightPx,0f)
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
            .padding(top = 23.dp)
    ) {
        NonSearchScreenBar(
            toolbarOffsetHeightPx = toolBarOffsetHeightPx.floatValue.roundToInt(),
            navController = navController,
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
                    .padding(bottom = 20.dp)
            ) {
                TvSeriesSection(
                    movieTitle = stringResource(id = R.string.top_movies),
                    showShimmer = mainMovieState.isLoading,
                    clickable = { /*TODO*/ },
                    mainUiState = mainMovieState
                )
                Spacer(modifier = Modifier.height(10.dp))
                TopRatedSeriesSection(
                    movieTitle = stringResource(id = R.string.tvSeries),
                    showShimmer = false,
                    clickable = {},
                    mainUiState = mainMovieState
                )
                Spacer(modifier = Modifier.height(10.dp))
                PopularTvSeriesSection(
                    movieTitle = stringResource(id = R.string.popular_series),
                    showShimmer = false,
                    clickable = { /*TODO*/ },
                    mainUiState = mainMovieState
                )
            }
            PullRefreshIndicator(
                refreshing = refreshing,
                state = refreshState,
                modifier = Modifier.align(
                    Alignment.Center
                )
                    .padding(top = 66.dp)
            )
        }
    }
}
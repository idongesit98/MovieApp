package com.zseni.filmapp.presentation.screens.upcomingMovies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zseni.filmapp.presentation.component.ItemInMovies
import com.zseni.filmapp.presentation.screens.mainUiScreen.MainMovieState
import com.zseni.filmapp.presentation.screens.mainUiScreen.MainUiEvent
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingMoviesScreen(
    mainMovieState: MainMovieState,
    navController: NavController,
    onEvent:(MainUiEvent) -> Unit
) {
    val toolbarHeightPx = with(LocalDensity.current){10.dp.roundToPx().toFloat()}
    val toolbarOffsetHeightPx = remember{ mutableFloatStateOf(0f) }
    val nestedScrollConnection = remember{
        object : NestedScrollConnection {
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
        onEvent(MainUiEvent.Refresh(category = "trendingScreen"))
        refreshing = false
    }
    val refreshState = rememberPullRefreshState(
        refreshing = refreshing,::refresh
    )
    Column{
        Text(
            modifier = Modifier.padding(top = 35.dp,start = 10.dp),
            text = "Trending Movies",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Italic
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
                    .padding(bottom = 30.dp)
            ) {
                if (mainMovieState.nowPlayingMoviesList.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp),
                        content = {
                            items(mainMovieState.nowPlayingMoviesList.size) { index ->
                                ItemInMovies(
                                    movie = mainMovieState.nowPlayingMoviesList[index],
                                    navController = navController
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                if (index >= mainMovieState.nowPlayingMoviesList.size - 1 && !mainMovieState.isLoading) {
                                    onEvent(MainUiEvent.Paginate("now_playing"))
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
@Preview
@Composable
fun PreviewTrendingMovies(){
    val navController = rememberNavController()
    TrendingMoviesScreen(
        mainMovieState = MainMovieState(
            isLoading = false,
            popularMoviesList = emptyList()
        ),
        navController = navController,
        onEvent = {}
    )
}

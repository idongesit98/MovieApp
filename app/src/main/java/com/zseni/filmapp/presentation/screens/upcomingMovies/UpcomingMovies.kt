package com.zseni.filmapp.presentation.screens.upcomingMovies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zseni.filmapp.presentation.screens.mainUiScreen.MainMovieState
import com.zseni.filmapp.presentation.component.ItemInMovies
import com.zseni.filmapp.presentation.screens.mainUiScreen.MainUiEvent
import com.zseni.filmapp.util.Constants

@Composable
fun UpcomingMovieScreen(
    mainMovieState: MainMovieState,
    navController: NavHostController,
    onEvent:(MainUiEvent) -> Unit
){
    Column(modifier = Modifier.fillMaxHeight()) {
        Text(
            text = "Upcoming Movies",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(top = 35.dp, start = 10.dp
            )
        )
        if (mainMovieState.upcomingMovieList.isEmpty()) {
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
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
            ) {
                items(mainMovieState.upcomingMovieList.size) { index ->
                    ItemInMovies(
                        movie = mainMovieState.upcomingMovieList[index],
                        navController = navController
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    if (index >= mainMovieState.upcomingMovieList.size - 1 && !mainMovieState.isLoading) {
                        onEvent(MainUiEvent.Paginate(Constants.UPCOMING))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewUpcomingMovies() {
    UpcomingMovieScreen(mainMovieState = MainMovieState(), navController = rememberNavController(), onEvent = {} )
}

package com.zseni.filmapp.presentation.screens.popularMovies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zseni.filmapp.presentation.component.ItemInMovies
import com.zseni.filmapp.presentation.screens.mainUiScreen.MainMovieState
import com.zseni.filmapp.presentation.screens.mainUiScreen.MainUiEvent

@Composable
fun PopularMovieScreen(
    mainMovieState:MainMovieState,
    navController: NavController,
    onEvent:(MainUiEvent) -> Unit
){
    if (mainMovieState.popularMoviesList.isEmpty()){
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator()
        }
    }else{
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
        ){
            items(mainMovieState.popularMoviesList.size){ index ->
                ItemInMovies(
                    movie = mainMovieState.popularMoviesList[index],
                    navController = navController
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (index >= mainMovieState.popularMoviesList.size -1 && !mainMovieState.isLoading){
                    onEvent(MainUiEvent.Paginate("popular"))
                }
            }
        }
    }
}

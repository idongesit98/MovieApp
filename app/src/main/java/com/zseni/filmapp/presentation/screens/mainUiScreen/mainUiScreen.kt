package com.zseni.filmapp.presentation.screens.mainUiScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.twotone.Favorite
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zseni.filmapp.R
import com.zseni.filmapp.presentation.screens.movieHome.HomeScreen
import com.zseni.filmapp.presentation.screens.popularMovies.PopularMovieScreen
import com.zseni.filmapp.presentation.screens.upcomingMovies.TvSeriesScreen
import com.zseni.filmapp.ui.theme.adaminaFont
import com.zseni.filmapp.util.Screen

data class BottomItem(
    val title:String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector
)
@Composable
fun MovieMainScreen(
    navController: NavController,
    mainMovieState: MainMovieState,
    onEvent: (MainUiEvent) -> Unit
){
    val bottomBarNavController = rememberNavController()
    val items = listOf(
        BottomItem(
            title = stringResource(id = R.string.home),
            selectedIcon = Icons.TwoTone.Home,
            unSelectedIcon = Icons.Outlined.Home
        ),
        BottomItem(
            title = stringResource(id = R.string.tvSeries),
            selectedIcon = Icons.TwoTone.LocalFireDepartment,
            unSelectedIcon = Icons.Outlined.LocalFireDepartment
        ),
        BottomItem(
            title = stringResource(id = R.string.popular),
            selectedIcon = Icons.TwoTone.LocalFireDepartment,
            unSelectedIcon = Icons.TwoTone.LocalFireDepartment
        ),
        BottomItem(
            title = stringResource(id = R.string.favourites),
            selectedIcon = Icons.TwoTone.Favorite,
            unSelectedIcon = Icons.Outlined.Favorite
        )
    )
    val selectedItem = rememberSaveable{
        mutableIntStateOf(0)
    }
    Scaffold(
        bottomBar = {
            NavigationBar {
                Row(modifier = Modifier
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
                ) {
                    items.forEachIndexed{index, bottomItem ->
                        NavigationBarItem(
                            selected = selectedItem.intValue == index,
                            onClick = {
                                selectedItem.intValue = index
                                when(selectedItem.intValue){
                                    0->{
                                        bottomBarNavController.popBackStack()
                                        bottomBarNavController.navigate(Screen.HomeScreen.route)
                                    }
                                    1 ->{
                                        bottomBarNavController.popBackStack()
                                        bottomBarNavController.navigate(
                                            "${Screen.TvSeriesScreen.route}?type=${"tvSeriesScreen"}"
                                        )
                                    }
                                    2 ->{
                                        bottomBarNavController.popBackStack()
                                        bottomBarNavController.navigate(
                                            "${Screen.PopularMovieScreen.route}?type=${"popularScreen"}"
                                        )
                                    }
                                    3 -> {
                                        bottomBarNavController.popBackStack()
                                        bottomBarNavController.navigate(Screen.FavouriteScreen.route)
//                                   "${Screen.UpcomingMovieList.route}?type=${"upcomingScreen"}"
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedItem.intValue){
                                        bottomItem.selectedIcon}else bottomItem.unSelectedIcon,
                                    contentDescription = bottomItem.title,
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            },
                            alwaysShowLabel = true,
                            label = {
                                Text(
                                    text = bottomItem.title,
                                    fontFamily = adaminaFont,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                            }
                        )

                    }

                }
            }
        }
    ){
        Column(modifier = Modifier
            .padding(it)
            .fillMaxSize()
        ) {
            BottomNavigationScreen(
                navController = navController,
                bottomNavController = bottomBarNavController,
                mainMovieState = mainMovieState,
                onEvent = onEvent,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            )

        }
    }
}

@Composable
fun BottomNavigationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    bottomNavController: NavHostController,
    mainMovieState: MainMovieState,
    onEvent: (MainUiEvent) -> Unit
){
    NavHost(
        navController = bottomNavController,
        startDestination = Screen.HomeScreen.route){
        composable(Screen.HomeScreen.route){
            HomeScreen(
                navController = navController,
                mainMovieState = mainMovieState,
                onEvent = onEvent
            )
        }
        composable(Screen.TvSeriesScreen.route){
            TvSeriesScreen(
                navController = navController,
                mainMovieState = mainMovieState,
            )
        }
        composable(Screen.PopularMovieScreen.route){
            PopularMovieScreen(
                mainMovieState = mainMovieState,
                navController = navController,
                onEvent = onEvent
            )
        }

    }
}

@Preview
@Composable
fun PreviewMainScreen() {
    val navController = rememberNavController()
    MovieMainScreen(
        navController = navController,
        mainMovieState = MainMovieState(),
        onEvent = {}
    )
}



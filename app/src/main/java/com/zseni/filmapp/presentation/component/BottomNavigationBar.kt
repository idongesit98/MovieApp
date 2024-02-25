package com.zseni.filmapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.zseni.filmapp.R
import com.zseni.filmapp.ui.theme.adaminaFont
import com.zseni.filmapp.util.Screen

data class BottomItem(
    val title:String,
    val selectedIcon:ImageVector,
    val unSelectedIcon:ImageVector
)
@Composable
fun BottomNavigationBar(
    bottomNavController: NavController
){
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
                               bottomNavController.popBackStack()
                               bottomNavController.navigate(Screen.HomeScreen.route)
                           }
                           1 ->{
                               bottomNavController.popBackStack()
                               bottomNavController.navigate(
                                   "${Screen.TvSeriesScreen.route}?type=${"tvSeriesScreen"}"
                               )
                           }
                           2 ->{
                               bottomNavController.popBackStack()
                               bottomNavController.navigate(
                                   "${Screen.PopularMovieScreen.route}?type=${"popularScreen"}"
                               )
                           }
                           3 -> {
                               bottomNavController.popBackStack()
                               bottomNavController.navigate(Screen.FavouriteScreen.route)
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

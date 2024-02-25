package com.zseni.filmapp.util

sealed class Screen(val route:String) {
    data object MainUiScreen:Screen("mainUiScreen")
    data object HomeScreen:Screen("homeScreen")
    data object PopularMovieScreen:Screen("popularMovies")
    data object UpcomingMovieScreen:Screen("upcomingMovies")
    data object DetailsScreen:Screen("detailsScreen")
    data object TrendingMovies:Screen("trendingMovies")
    data object FavouriteScreen:Screen("favourites")
    data object SearchScreen:Screen("searchScreen")
    data object TvSeriesScreen:Screen("tvSeries")
    data object RecommendedScreen:Screen("recommendedScreen")
    data object TopRatedScreen:Screen("topRatedScreen")
    data object TvDetailsScreen:Screen("tvDetailsScreen")
}
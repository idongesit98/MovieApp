package com.zseni.filmapp.presentation.screens.mainUiScreen

import com.zseni.filmapp.domain.model.Genre
import com.zseni.filmapp.domain.model.Movie

data class MainMovieState(
    val isLoading:Boolean = false,
    val isRefreshing:Boolean = false,
    val popularMovieListPage:Int = 1,
    val upComingMovieListPage:Int = 1,
    val topRatedTvSeriesPage:Int = 1,
    val topRatedMovieListPage:Int = 1,
    val onTheAirTvSeriesPage:Int = 1,
    val popularTvSeriesPage:Int = 1,
    val nowPlayingMoviesPage:Int = 1,
    val trendingAllPage:Int = 1,
    val isCurrentPopularScreen:Boolean = true,
    val isCurrentHomeScreen:Boolean = true,

    val popularMoviesList :List<Movie> = emptyList(),
    val upcomingMovieList:List<Movie> = emptyList(),
    val topRatedTvSeriesList:List<Movie> = emptyList(),
    val topRatedTvMovieList : List<Movie> = emptyList(),
    val airingTodayTvSeriesList:List<Movie> = emptyList(),
    val popularTvSeriesList:List<Movie> = emptyList(),
    val latestMoviesList:List<Movie> = emptyList(),
    val trendingAllList:List<Movie> = emptyList(),
    val nowPlayingMoviesList:List<Movie> = emptyList(),


    val trendingMoviesList:List<Movie> = emptyList(),
    // for popularTvSeriesList + topRatedTvSeriesList
    val tvSeriesList:List<Movie> = emptyList(),

    val moviesGenresList:List<Genre> = emptyList(),
    val tvGenresList:List<Genre> = emptyList(),
    //This contains nowPlayingTvseries + nowPlayingmoviesList
    val recommendedMoviesList:List<Movie> = emptyList(),
    // matching items in:
    // recommendedAllList and trendingAllList
    val specialList: List<Movie> = emptyList(),
    val topRatedAllList: List<Movie> = emptyList()

//    val moviesGenresList: List<Genre> = emptyList(),
//    val tvGenresList: List<Genre> = emptyList(),



)

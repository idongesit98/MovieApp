package com.zseni.filmapp.domain.repository

import com.zseni.filmapp.domain.model.Movie
import com.zseni.filmapp.util.Resource
import kotlinx.coroutines.flow.Flow

//Check the getMovieRepo if type:String that is not included is the cause of the issues for details not working
interface MovieRepository {
    suspend fun getMovie(
        id:Int,
    ):Flow<Resource<Movie>>
    suspend fun getMovieList(
        forceFetchFromRemote:Boolean,
        category:String,
        isRefresh:Boolean,
        page:Int
    ):Flow<Resource<List<Movie>>>

    suspend fun getTv(
        id:Int
    ):Flow<Resource<Movie>>
    suspend fun getTvList(
        forceFetchedFromRemote:Boolean,
        category:String,
        isRefresh:Boolean,
        page:Int
    ):Flow<Resource<List<Movie>>>

    suspend fun getTrendingList(
        forceFetchedFromRemote:Boolean,
        isRefresh:Boolean,
        time:String,
        page:Int
    ):Flow<Resource<List<Movie>>>

    suspend fun insertMovie(movie: Movie)
    suspend fun updateMovie(movie: Movie)

}
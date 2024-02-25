package com.zseni.filmapp.data.remote

import com.zseni.filmapp.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path("category") category:String,
        @Query("page") page:Int,
        @Query("api_key") apikey:String = API_KEY
    ):MovieListDto


    @GET("tv/{category}")
    suspend fun getTvList(
        @Path("category") category:String,
        @Query("page") page:Int,
        @Query("api_key") apikey:String = API_KEY
    ):MovieListDto

    @GET("trending/all/{time_window}")
    suspend fun getTrendingList(
        @Path("time_window") time:String,
        @Query("page") page:Int,
        @Query("api_key") apikey:String = API_KEY
    ):MovieListDto

    @GET("search/multi")
    suspend fun searchMovies(
        @Query("query") query:String,
        @Query("page") page: Int,
        @Query("api_key") apikey: String = API_KEY
    ):MovieListDto

    @GET("genre/{category}/list")
    suspend fun getGenres(
        @Path("category") category: String,
        @Query("api_key") apikey: String = API_KEY
    ):GenreListDto
}
package com.zseni.filmapp.data.repository

import com.zseni.filmapp.data.local.movie.MovieDatabase
import com.zseni.filmapp.data.mapper.toMediaEntity
import com.zseni.filmapp.data.mapper.toMovie
import com.zseni.filmapp.data.mapper.toMovieEntity
import com.zseni.filmapp.data.remote.MovieApi
import com.zseni.filmapp.domain.model.Movie
import com.zseni.filmapp.domain.repository.MovieRepository
import com.zseni.filmapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieRepoImpl @Inject constructor(
    private val apiService:MovieApi,
    private val movieDatabase: MovieDatabase
):MovieRepository {
    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        isRefresh:Boolean,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))

            val localMovieList = movieDatabase.movieDao.getMoviesByCategory(category)

            val loadLocalMovie = localMovieList.isNotEmpty() && !forceFetchFromRemote && !isRefresh

            if (loadLocalMovie) {
                emit(Resource.Success(
                    data = localMovieList.map { movieEntity ->
                        movieEntity.toMovie(
                            category = category
                        )
                    }
                ))
                emit(Resource.Loading(false))
                return@flow
            }

            if (isRefresh){
                movieDatabase.movieDao.deleteMovies(category)
            }

            val movieListFromRemote = try {
                apiService.getMoviesList(category,page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error leading movies"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error leading movies"))
                return@flow
            }

            val movieEntities = movieListFromRemote.results.let {
                it.map { movieDto ->
                    movieDto.toMovieEntity(
                        category = category
                    )
                }
            }
            movieDatabase.movieDao.upsertMovieList(movieEntities)

            emit(Resource.Success(
                movieEntities.map { it.toMovie(
                    category = category
                ) }
            ))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMovie(
        id: Int
    ): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))

            val movieEntity = movieDatabase.movieDao.getMovieById(id)



            if (movieEntity != null) {
                emit(
                    Resource.Success(movieEntity.toMovie(
                        category = movieEntity.category,
                    ))
                )
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error("No error"))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getTvList(
        forceFetchedFromRemote: Boolean,
        category: String,
        isRefresh: Boolean,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))

            val localTvList = movieDatabase.movieDao.getTvListByCategory(category)

            val loadLocalTv = localTvList.isNotEmpty() && !forceFetchedFromRemote && !isRefresh

            if (loadLocalTv) {
                emit(Resource.Success(
                    data = localTvList.map { movieEntity ->
                        movieEntity.toMovie(
                            category = category
                        )
                    }
                ))
                emit(Resource.Loading(false))
                return@flow
            }
            if (isRefresh){
                movieDatabase.movieDao.deleteMovies(category)
            }

            val tvListFromRemote = try {
                apiService.getTvList(category, page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading tvSeries"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading tvSeries"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading tvSeries"))
                return@flow
            }


            val tvEntities = tvListFromRemote.results.let {
                it.map { movieDto ->
                    movieDto.toMovieEntity(
                        category = category
                    )
                }
            }
            movieDatabase.movieDao.upsertMovieList(tvEntities)
            emit(Resource.Success(
                tvEntities.map { it.toMovie(
                    category = category,
                ) }
            ))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getTrendingList(
        forceFetchedFromRemote: Boolean,
        isRefresh: Boolean,
        time: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))
            val localMovieList = movieDatabase.movieDao.getTrendingMovies("trending")
            val shouldJustLoadFromCache = localMovieList.isNotEmpty() && !forceFetchedFromRemote
            if (shouldJustLoadFromCache){
                emit(Resource.Success(
                    data = localMovieList.map {
                        it.toMovie(
                            category = "trending"
                        )
                    }
                ))
                emit(Resource.Loading(false))
                return@flow
            }
            var searchPage = page
            if (isRefresh){
                movieDatabase.movieDao.deleteMovies("trending")
                searchPage = 1
            }
            val remoteMediaList = try {
                apiService.getTrendingList(time, searchPage).results
            }catch (e:IOException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                emit(Resource.Loading(false))
                return@flow
            }catch (e:HttpException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                emit(Resource.Loading(false))
                return@flow
            }
            remoteMediaList.let {movieList ->
                val movie = movieList.map {
                    it.toMovie(
                        category = "day"
                    )
                }
                val entities = movieList.map {
                    it.toMovieEntity(
                        category = "day"
                    )
                }
                movieDatabase.movieDao.upsertMovieList(entities)
                emit(Resource.Success(data = movie))

                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun insertMovie(movie: Movie) {
        movieDatabase.movieDao.insertMovie(
            movieItem = movie.toMediaEntity()
        )
    }

    override suspend fun updateMovie(movie: Movie) {
        movieDatabase.movieDao.upsertMovieList(
            movieList = listOf(movie.toMediaEntity())
        )
    }

    override suspend fun getTv(
        id: Int
    ): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))
            val tvEntity = movieDatabase.movieDao.getTvById(id)

            if(tvEntity != null){
                emit(
                    Resource.Success(tvEntity.toMovie(
                        tvEntity.category))
                )
                emit(Resource.Loading(false))
                return@flow
            }
            emit(Resource.Error("No Error"))
            emit(Resource.Loading(false))

        }
    }
}

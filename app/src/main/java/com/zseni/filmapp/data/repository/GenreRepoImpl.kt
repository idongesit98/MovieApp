package com.zseni.filmapp.data.repository

import com.zseni.filmapp.data.local.genre.GenreDatabase
import com.zseni.filmapp.data.local.genre.GenreEntity
import com.zseni.filmapp.data.remote.MovieApi
import com.zseni.filmapp.domain.model.Genre
import com.zseni.filmapp.domain.repository.GenreRepository
import com.zseni.filmapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenreRepoImpl @Inject constructor(
    private val apiService: MovieApi,
    genreDb: GenreDatabase
):GenreRepository {
    private val genreDao = genreDb.genreDao
    override suspend fun getGenre(
        forceFetchFromRemote: Boolean,
        category: String
    ): Flow<Resource<List<Genre>>> {
        return flow {
            emit(Resource.Loading(true))
            val genreEntity = genreDao.getGenresList(category)
            if (genreEntity.isNotEmpty() && !forceFetchFromRemote){
                emit(Resource.Success(
                    genreEntity.map { genreEntity ->
                        Genre(
                            id = genreEntity.id,
                            name = genreEntity.name,
                        )
                    }
                ))
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteGenreList = try {
                apiService.getGenres(category).genres
            }catch (e:IOException){
                e.printStackTrace()
                emit(Resource.Error("Couldn't load genre data"))
                emit(Resource.Loading(false))
                return@flow
            }catch (e:HttpException){
                e.printStackTrace()
                emit(Resource.Error(""))
                emit(Resource.Loading(false))
                return@flow
            }
            remoteGenreList.let {
                genreDao.insertGenres(remoteGenreList.map { remoteGenre->
                    GenreEntity(
                        id = remoteGenre.id,
                        name = remoteGenre.name,
                        category = category
                    )
                })
                emit(Resource.Success(remoteGenreList))
                emit(Resource.Loading(false))

            }

        }
    }
}
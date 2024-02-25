package com.zseni.filmapp.domain.repository

import com.zseni.filmapp.domain.model.Genre
import com.zseni.filmapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    suspend fun getGenre(
        forceFetchFromRemote:Boolean,
        category:String
    ):Flow<Resource<List<Genre>>>

}
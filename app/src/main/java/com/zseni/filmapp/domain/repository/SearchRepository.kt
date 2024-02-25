package com.zseni.filmapp.domain.repository

import com.zseni.filmapp.domain.model.Movie
import com.zseni.filmapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun getSearchList(
        forceFetchedFromRemote:Boolean,
        query:String,
        page:Int,
    ):Flow<Resource<List<Movie>>>
}
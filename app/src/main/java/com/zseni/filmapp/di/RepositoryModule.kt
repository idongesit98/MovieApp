package com.zseni.filmapp.di

import com.zseni.filmapp.data.repository.MovieRepoImpl
import com.zseni.filmapp.data.repository.SearchRepoImpl
import com.zseni.filmapp.domain.repository.MovieRepository
import com.zseni.filmapp.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMovieListRepository(
        movieRepoImpl: MovieRepoImpl
    ):MovieRepository

    @Binds
    @Singleton
    abstract fun bindSearchMovieRepository(
        searchRepoImpl: SearchRepoImpl
    ):SearchRepository
}
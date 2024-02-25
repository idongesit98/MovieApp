package com.zseni.filmapp.presentation.screens.movieDetails

import com.zseni.filmapp.domain.model.Movie

data class DetailsState(
    val isLoading:Boolean = false,
    val movie:Movie? = null,
    val tv:Movie? = null
)

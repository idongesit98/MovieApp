package com.zseni.filmapp.presentation.screens.searchScreen.components

import androidx.compose.runtime.Composable
import com.zseni.filmapp.domain.model.Genre

@Composable
fun genresProvider(
    genre_ids: List<Int>,
    allGenres: List<Genre>
): String {
    return allGenres
        .filter { it.id in genre_ids }
        .joinToString(separator = " - ") { it.name }
}
/**
 * This code uses filter to select only genres with ID present in 'genre_ids'
 * and 'joinToString' to concatenate their names with a separator
 */
package com.zseni.filmapp.presentation.screens.movieHome

/**
 * This is for the home and popular screen if not implemented delete it out
 */
sealed class UiEvent {
    data class Paginate(val category:String): UiEvent()
    data object Navigate: UiEvent()
}
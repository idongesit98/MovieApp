package com.zseni.filmapp.presentation.screens.mainUiScreen

sealed class MainUiEvent {
    data class Refresh(val category:String):MainUiEvent()
    data class Paginate(val category: String):MainUiEvent()
}
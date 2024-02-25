package com.zseni.filmapp.presentation.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zseni.filmapp.domain.repository.MovieRepository
import com.zseni.filmapp.presentation.screens.movieDetails.DetailsState
import com.zseni.filmapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
     savedStateHandle: SavedStateHandle
):ViewModel() {

    private val movieId = savedStateHandle.get<Int>("movieId")
    private val tvId = savedStateHandle.get<Int>("tvId")

    private val _detailsState = MutableStateFlow(DetailsState())
    val detailsState = _detailsState.asStateFlow()
    init {
        getMovie(movieId?: -1)
        getTv(tvId?:-1)
    }

    private fun getMovie(id:Int){
        viewModelScope.launch {
            _detailsState.update {
                it.copy(isLoading = true)
            }

           movieRepository.getMovie(id).collectLatest { result ->
               when(result){
                   is Resource.Error ->{
                       _detailsState.update {
                           it.copy(isLoading = false)
                       }
                   }

                   is Resource.Loading ->{
                       _detailsState.update {
                           it.copy(isLoading = result.isLoading)
                       }
                   }

                   is Resource.Success -> {
                       result.data?.let { movie->
                           _detailsState.update {
                               it.copy(movie = movie )
                           }
                       }
                   }
               }
           }
        }
    }

    private fun getTv(id: Int){
        viewModelScope.launch {
            _detailsState.update {
                it.copy(isLoading = true)
            }
            movieRepository.getTv(id).collectLatest { result ->
                when(result){
                    is Resource.Error ->{
                        _detailsState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success ->{
                        result.data?.let {tv ->
                            _detailsState.update {
                                it.copy(tv = tv)
                            }
                        }
                    }
                    is Resource.Loading->{
                        _detailsState.update {
                            it.copy(isLoading = result.isLoading)
                        }

                    }
                }

            }
        }
    }
}
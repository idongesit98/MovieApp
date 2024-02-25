package com.zseni.filmapp.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zseni.filmapp.domain.model.Movie
import com.zseni.filmapp.domain.repository.MovieRepository
import com.zseni.filmapp.presentation.screens.mainUiScreen.MainMovieState
import com.zseni.filmapp.presentation.screens.mainUiScreen.MainUiEvent
import com.zseni.filmapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository
):ViewModel() {
    private val TAG = MovieListViewModel::class.simpleName

    private val _mainMovieState = MutableStateFlow(MainMovieState())
    val movieListState = _mainMovieState.asStateFlow()

    init {
        getPopularMovies(false)
        getUpcomingMovies(false)
        getPopularTvSeries(false)
        getTopRatedTvSeries(false)
        getAiringTvSeries(false)
        getTopRatedMovieSeries(false)
        getTrendingAll(false)
        getNowPlayingMovies(false)
    }

    fun onEvent(event: MainUiEvent){
        when(event){
            is MainUiEvent.Refresh ->{
                _mainMovieState.update {
                    it.copy(
                        isLoading = true
                    )
                }
                when(event.category){
                    "homeScreen" -> {
                        getTrendingAll(
                            fetchFromRemote = true,
                            isRefresh = true
                        )
                        getTopRatedMovieSeries(
                            forceFetchFromRemote = true,
                            isRefresh = true
                        )
                        getNowPlayingMovies(
                            fetchFromRemote = true,
                            isRefresh = true
                        )
                        getTopRatedMovieSeries(
                            forceFetchFromRemote = true,
                            isRefresh = true
                        )
                    }
                    "popularScreen" ->{
                        getPopularMovies(
                            forceFetchFromRemote = true,
                            isRefresh = true
                        )
                    }
                    "trendingScreen" ->{
                        getNowPlayingMovies(
                            fetchFromRemote = true,
                            isRefresh = true
                        )
                    }
                    "tvSeries" ->{
                        getAiringTvSeries(
                            forceFetchFromRemote = true,
                            isRefresh = true
                        )
                        getTopRatedMovieSeries(
                            forceFetchFromRemote = true,
                            isRefresh = true
                        )
                        getPopularTvSeries(
                            forceFetchFromRemote = true,
                            isRefresh = true
                        )
                    }
                    "recommendedScreen" ->{
                        getNowPlayingMovies(
                            fetchFromRemote = true,
                            isRefresh = true
                        )
                        getTopRatedMovieSeries(
                            forceFetchFromRemote = true,
                            isRefresh = true
                        )
                        getUpcomingMovies(
                            forceFetchFromRemote = true,
                            isRefresh = true
                        )
                    }
                    "topRatedScreen" ->{
                        getTopRatedTvSeries(
                            forceFetchFromRemote = true,
                            isRefresh = true
                        )
                        getTopRatedMovieSeries(
                            forceFetchFromRemote = true,
                            isRefresh = true
                        )
                    }
                }
            }
            is MainUiEvent.Paginate ->{
                when (event.category) {
                    "popular" -> getPopularMovies(true)
                    "upcoming" -> getUpcomingMovies(true)
                }
            }
        }
    }


    private fun getPopularMovies(forceFetchFromRemote:Boolean,isRefresh:Boolean = false){
        viewModelScope.launch {
            _mainMovieState.update {
                it.copy(isLoading = true)
            }
            movieRepository.getMovieList(
                forceFetchFromRemote,
                "popular",
                isRefresh,
                movieListState.value.popularMovieListPage
            ).collect { result ->
                when(result){
                    is Resource.Error ->{
                        _mainMovieState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success ->{
                        result.data?.let {popularList ->
                            _mainMovieState.update {
                                it.copy(
                                    popularMoviesList = movieListState.value.popularMoviesList
                                    + popularList.shuffled(),
                                    popularMovieListPage = movieListState.value.popularMovieListPage +1
                                )
                            }

                        }
                    }

                    is Resource.Loading ->{
                        _mainMovieState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }

            }
        }

    }

    private fun getUpcomingMovies(forceFetchFromRemote: Boolean,isRefresh: Boolean = false) {
        viewModelScope.launch {
            _mainMovieState.update {
                it.copy(isLoading = true)
            }

            movieRepository.getMovieList(
                forceFetchFromRemote,
                category = "upcoming",
                isRefresh,
                movieListState.value.upComingMovieListPage
            ).collectLatest { result ->
                when(result){
                    is Resource.Error ->{
                        _mainMovieState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Success ->{
                        result.data?.let {upcomingList ->
                            _mainMovieState.update {
                                it.copy(
                                    upcomingMovieList = movieListState.value.upcomingMovieList
                                            + upcomingList.shuffled(),
                                    upComingMovieListPage = movieListState.value.upComingMovieListPage +1
                                )
                            }
                        }
                    }

                    is Resource.Loading ->{
                        _mainMovieState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }

            }
        }
    }

    private fun getTopRatedTvSeries(forceFetchFromRemote: Boolean,isRefresh: Boolean = false){
        viewModelScope.launch {
            _mainMovieState.update {
                it.copy(isLoading = true)
            }

            movieRepository.getTvList(
                forceFetchFromRemote,
                category = "top_rated",
                isRefresh,
                movieListState.value.topRatedTvSeriesPage
            ).collectLatest {result ->
                when(result){
                    is Resource.Error ->{
                        _mainMovieState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success ->{
                        result.data?.let {topRatedSeries ->
                            _mainMovieState.update {
                                it.copy(
                                    topRatedTvSeriesPage = movieListState.value.topRatedTvSeriesPage +1,
                                    topRatedTvSeriesList = movieListState.value.topRatedTvSeriesList
                                            + topRatedSeries.shuffled()
                                )
                            }

                        }
                    }
                    is Resource.Loading ->{
                        _mainMovieState.update {
                            it.copy(result.isLoading)
                        }
                    }
                }
            }
        }


    }

    private fun getTopRatedMovieSeries(forceFetchFromRemote: Boolean,isRefresh: Boolean = false){
        viewModelScope.launch {
            _mainMovieState.update {
                it.copy(isLoading = true)
            }

            movieRepository.getTvList(
                forceFetchFromRemote,
                category = "top_rated",
                isRefresh,
                movieListState.value.topRatedMovieListPage
            ).collectLatest {result ->
                when(result){
                    is Resource.Error ->{
                        _mainMovieState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Success ->{
                        result.data?.let {topRatedMovies ->
                            _mainMovieState.update {
                                it.copy(
                                    topRatedMovieListPage = movieListState.value.topRatedMovieListPage +1,
                                    topRatedTvMovieList = movieListState.value.topRatedTvMovieList
                                            + topRatedMovies.shuffled()
                                )
                            }
                           Log.d(TAG, "TopRatedSeries = ${getTopRatedTvSeries(forceFetchFromRemote, isRefresh)}")
                        }
                    }

                    is Resource.Loading ->{
                        _mainMovieState.update {
                            it.copy(result.isLoading)
                        }
                    }
                }

            }
        }


    }

    private fun getPopularTvSeries(forceFetchFromRemote:Boolean,isRefresh: Boolean = false){
        viewModelScope.launch {
            _mainMovieState.update {
                it.copy(isLoading = true)
            }
            movieRepository.getTvList(
                forceFetchFromRemote,
                category = "popular",
                isRefresh,
                movieListState.value.popularTvSeriesPage
            ).collectLatest { result ->
                when(result){
                    is Resource.Success ->{
                        result.data?.let {popularTvSeries ->
                            _mainMovieState.update {
                                it.copy(
                                    popularTvSeriesPage = movieListState.value.popularTvSeriesPage +1,
                                    popularTvSeriesList = movieListState.value.popularTvSeriesList
                                            + popularTvSeries.shuffled()
                                )
                            }

                        }
                    }
                    is Resource.Loading ->{
                        _mainMovieState.update {
                            it.copy(result.isLoading)
                        }
                    }

                    is Resource.Error ->{
                        _mainMovieState.update {
                            it.copy(isLoading = false)
                        }
                    }
                }
            }
        }
    }

    private fun getAiringTvSeries(forceFetchFromRemote:Boolean,isRefresh: Boolean = false){
        viewModelScope.launch {
            _mainMovieState.update {
                it.copy(isLoading = true)
            }
            movieRepository.getTvList(
                forceFetchFromRemote,
                category = "airing_today",
                isRefresh,
                movieListState.value.onTheAirTvSeriesPage
            ).collectLatest { result ->
                when(result){
                    is Resource.Success ->{
                        result.data?.let {onAirTvSeries ->
                            _mainMovieState.update {
                                it.copy(
                                    onTheAirTvSeriesPage = movieListState.value.onTheAirTvSeriesPage +1,
                                    airingTodayTvSeriesList = movieListState.value.airingTodayTvSeriesList
                                            + onAirTvSeries.shuffled(),
                                )
                            }
                        }
                    }
                    is Resource.Loading ->{
                        _mainMovieState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Error ->{
                        _mainMovieState.update {
                            it.copy(isLoading = false)
                        }
                    }

                }
            }
        }
    }

    private fun getTrendingAll(
        fetchFromRemote:Boolean,
        isRefresh: Boolean = false
    ){
        viewModelScope.launch {
            movieRepository.getTrendingList(
                fetchFromRemote,
                isRefresh,
                "day",
                movieListState.value.trendingAllPage
            ).collect { result->
                when(result){
                    is Resource.Success ->{
                        result.data?.let {trendingMovies->
                            val shuffledMovieList = trendingMovies.toMutableList()
                            shuffledMovieList.shuffle()
                            if (isRefresh){
                                _mainMovieState.update {
                                    it.copy(
                                        trendingAllList = shuffledMovieList.toList(),
                                        trendingAllPage = movieListState.value.trendingAllPage + 1
                                    )

                                }
                            }
                            createRecommendedMovieAllList(
                                movieList = trendingMovies,
                                isRefresh = isRefresh
                            )
                        }
                    }
                    is Resource.Error ->{
                        _mainMovieState.update {
                            it.copy(isLoading = false)
                        }
                    }

                    is Resource.Loading ->{
                        _mainMovieState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                }
            }
        }
    }

    private fun getNowPlayingMovies(
        fetchFromRemote: Boolean,
        isRefresh: Boolean = false
    ){
        viewModelScope.launch {
            movieRepository.getMovieList(
                fetchFromRemote,
                "now_playing",
                isRefresh,
                movieListState.value.nowPlayingMoviesPage
            ).collect { result ->
                when(result) {
                    is Resource.Success ->{
                        result.data?.let { nowPlaying ->
                            val shuffledMovieList = nowPlaying.toMutableList()
                            shuffledMovieList.shuffle()

                            if (isRefresh){
                                _mainMovieState.update {
                                    it.copy(
                                        nowPlayingMoviesList = shuffledMovieList.toList(),
                                        nowPlayingMoviesPage = movieListState.value.nowPlayingMoviesPage + 1
                                    )
                                }
                            }else{
                                _mainMovieState.update {
                                    it.copy(
                                        nowPlayingMoviesList = movieListState.value.nowPlayingMoviesList + shuffledMovieList.toList(),
                                        nowPlayingMoviesPage = movieListState.value.nowPlayingMoviesPage + 1
                                    )
                                }
                            }
                            createRecommendedMovieAllList(
                                movieList = nowPlaying,
                                isRefresh = isRefresh
                            )
                        }
                    }
                    is Resource.Error->{
                        _mainMovieState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is Resource.Loading ->{
                        _mainMovieState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }


    private fun createSpecialList(
        movieList: List<Movie>,
        isRefresh: Boolean = false
    ){
        if (isRefresh){
            _mainMovieState.update {
                it.copy(
                    specialList = emptyList()
                )
            }
        }
        val shuffledMovieList = movieList.take(7).toMutableList()
        shuffledMovieList.shuffle()
        if (isRefresh){
            _mainMovieState.update {
                it.copy(
                    specialList = shuffledMovieList
                )
            }
        }else{
            _mainMovieState.update {
                it.copy(
                    specialList = _mainMovieState.value.specialList + shuffledMovieList
                )
            }
        }
    }

    private fun createTvSeriesList(
        movieList: List<Movie>,
        isRefresh: Boolean
    ){
        val shuffledMovieList = movieList.toMutableList()
        shuffledMovieList.shuffle()
        if (isRefresh){
            _mainMovieState.update {
                it.copy(
                    tvSeriesList = movieListState.value.tvSeriesList + shuffledMovieList.toList()
                )
            }
        }
    }

    private fun createAllListForTopRatedMovies(
        movieList: List<Movie>,
        isRefresh: Boolean
    ){
        val shuffledMovieList = movieList.toMutableList()
        shuffledMovieList.shuffle()
        if (isRefresh){
            _mainMovieState.update {
                it.copy(
                    topRatedAllList = movieListState.value.topRatedAllList + shuffledMovieList.toList()

                )
            }
        }
    }

    private fun createRecommendedMovieAllList(
        movieList: List<Movie>,
        isRefresh: Boolean
    ){
        val shuffledMovieList = movieList.toMutableList()
        shuffledMovieList.shuffle()
        if (isRefresh){
            _mainMovieState.update {
                it.copy(
                    recommendedMoviesList = shuffledMovieList.toList()
                )
            }
        }else{
            _mainMovieState.update {
                it.copy(
                    recommendedMoviesList = movieListState.value.recommendedMoviesList + shuffledMovieList.toList()
                )
            }
        }
        createSpecialList(
            movieList = movieList,
            isRefresh = isRefresh
        )
    }
}
package info.firozansari.movieapp.presentation.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import info.firozansari.movieapp.data.repository.MoviesRepository
import info.firozansari.movieapp.domain.responses.MovieResult
import info.firozansari.movieapp.presentation.BaseViewModel
import info.firozansari.movieapp.presentation.Config.ANIME_SERIES
import info.firozansari.movieapp.presentation.Config.BOLLYWOOD_MOVIES
import info.firozansari.movieapp.presentation.Config.NEWLY_LAUNCHED
import info.firozansari.movieapp.presentation.Config.POPULAR_MOVIES
import info.firozansari.movieapp.presentation.Config.POPULAR_TV_SHOWS
import info.firozansari.movieapp.presentation.Config.TOP_RATED_MOVIES
import info.firozansari.movieapp.presentation.Config.TRENDING_MOVIES
import info.firozansari.movieapp.presentation.Config.TRENDING_TV_SHOWS

class MovieDetailViewModel @AssistedInject constructor(
    private val movieRepo: MoviesRepository,
    @Assisted
    private val mediaCategory: String
) : BaseViewModel(movieRepo) {

    @AssistedFactory
    interface TrendingViewModelFactory {
        fun create(mediaCategory: String): MovieDetailViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun providesFactory(
            assistedFactory: TrendingViewModelFactory,
            mediaCategory: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(mediaCategory) as T
            }
        }
    }

    lateinit var categoryWiseMediaList: LiveData<PagingData<MovieResult>>

    init {
        when (mediaCategory) {
            TRENDING_MOVIES -> getTrendingMovies()
            TRENDING_TV_SHOWS -> getTrendingTvShows()
            NEWLY_LAUNCHED -> getNewlyLaunchedMovies()
            POPULAR_MOVIES -> getPopularMovies()
            POPULAR_TV_SHOWS -> getPopularTvShows()
            TOP_RATED_MOVIES -> getTopRatedMovies()
            ANIME_SERIES -> getAnimeSeries()
            BOLLYWOOD_MOVIES -> getBollywoodMovies()
        }
    }

    private fun getTrendingMovies() {
        categoryWiseMediaList = movieRepo.fetchTrendingMoviesPaging().cachedIn(viewModelScope)
    }

    private fun getTrendingTvShows() {
        categoryWiseMediaList = movieRepo.fetchTrendingTvShowsPaging().cachedIn(viewModelScope)
    }

    private fun getNewlyLaunchedMovies() {
        categoryWiseMediaList = movieRepo.fetchNowPlayingMoviesPaging().cachedIn(viewModelScope)
    }

    private fun getPopularMovies() {
        categoryWiseMediaList = movieRepo.fetchPopularMoviesPaging().cachedIn(viewModelScope)
    }

    private fun getPopularTvShows() {
        categoryWiseMediaList = movieRepo.fetchPopularTvShowsPaging().cachedIn(viewModelScope)
    }

    private fun getTopRatedMovies() {
        categoryWiseMediaList = movieRepo.fetchTopRatedMoviesPaging().cachedIn(viewModelScope)
    }

    private fun getAnimeSeries() {
        categoryWiseMediaList = movieRepo.fetchAnimeSeriesPaging().cachedIn(viewModelScope)
    }

    private fun getBollywoodMovies() {
        categoryWiseMediaList = movieRepo.fetchBollywoodMoviesPaging().cachedIn(viewModelScope)
    }
}

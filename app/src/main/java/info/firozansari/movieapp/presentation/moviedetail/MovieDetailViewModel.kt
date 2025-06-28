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
import dagger.hilt.android.lifecycle.HiltViewModel
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

@HiltViewModel
class MovieDetailViewModel @AssistedInject constructor(
    private val movieRepo: MoviesRepository,
) : BaseViewModel(movieRepo) {

    @AssistedFactory
    interface MovieDetailViewModelFactory {
        fun create(mediaCategory: String): MovieDetailViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun providesFactory(
            assistedFactory: MovieDetailViewModelFactory,
            movieId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(movieId) as T
            }
        }
    }

    lateinit var categoryWiseMediaList: LiveData<PagingData<MovieResult>>

    init {

    }

}

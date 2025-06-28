package info.firozansari.movieapp.presentation.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import info.firozansari.movieapp.data.repository.MoviesRepository
import info.firozansari.movieapp.domain.responses.MovieDetailResponse
import info.firozansari.movieapp.presentation.BaseViewModel
import info.firozansari.movieapp.presentation.util.Resource
import kotlinx.coroutines.launch


class MovieDetailViewModel @AssistedInject constructor(
    private val movieRepo: MoviesRepository,
    @Assisted
    private val movieId: String
) : BaseViewModel(movieRepo) {

    private val _movieDetail = MutableLiveData<Resource<MovieDetailResponse>>()
    val movieDetail: LiveData<Resource<MovieDetailResponse>> = _movieDetail

    @AssistedFactory
    interface MovieDetailViewModelFactory {
        fun create(movieId: String): MovieDetailViewModel
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

    init {
        getMovieDetail(movieId.toInt())
    }

    fun getMovieDetail(movieId: Int) = viewModelScope.launch {
        _movieDetail.postValue(Resource.Loading())
        _movieDetail.postValue(movieRepo.fetchMovieDetail(movieId = movieId))
    }
}

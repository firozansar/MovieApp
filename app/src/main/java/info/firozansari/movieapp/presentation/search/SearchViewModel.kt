package info.firozansari.movieapp.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.firozansari.movieapp.data.repository.MoviesRepository
import info.firozansari.movieapp.domain.model.responses.MovieListResponse
import info.firozansari.movieapp.presentation.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel // We need to remove this annotation if we want to use Assisted Injection
class SearchViewModel @Inject constructor(
    private val moviesRepo: MoviesRepository,
) : ViewModel() {

    var isMovie = true

    private val _trendingMedia = MutableLiveData<Resource<MovieListResponse>>()
    val trendingMedia: LiveData<Resource<MovieListResponse>> = _trendingMedia

    private val _searchedMedia = MutableLiveData<Resource<MovieListResponse>>()
    val searchedMedia: LiveData<Resource<MovieListResponse>> = _searchedMedia

    fun trendingMovies(isMovie: Boolean) = viewModelScope.launch {
        // This will fetch weekly trending data
        _trendingMedia.postValue(Resource.Loading())
        if (isMovie)
            _trendingMedia.postValue(moviesRepo.fetchTrendingMovies())
        else
            _trendingMedia.postValue(moviesRepo.fetchTrendingTvShows())
    }

    fun searchMedia(isMovie: Boolean, searchQuery: String) = viewModelScope.launch {
        _searchedMedia.postValue(Resource.Loading())
        if (isMovie)
            _searchedMedia.postValue(moviesRepo.fetchSearchedMovies(searchQuery))
        else
            _searchedMedia.postValue(moviesRepo.fetchSearchedTvShows(searchQuery))
    }
}

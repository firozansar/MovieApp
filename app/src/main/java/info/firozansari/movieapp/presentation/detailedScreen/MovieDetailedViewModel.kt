package info.firozansari.movieapp.presentation.detailedScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import info.firozansari.movieapp.domain.model.Movie
import info.firozansari.movieapp.domain.usecase.FavoriteMovieUseCase
import info.firozansari.movieapp.presentation.ViewModelResult
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailedViewModel @Inject constructor(private val favoriteMovieUseCase: FavoriteMovieUseCase) : ViewModel() {

  private var movie: Movie? = null
  val onFavoriteMovieEvent = MutableLiveData<ViewModelResult>()

  fun setup(movie: Movie) {
    this.movie = movie
    GlobalScope.launch {
      try {
        movie.isFavorite = favoriteMovieUseCase.isFavorite(movie)
        onFavoriteMovieEvent.postValue(ViewModelResult.Success)
      } catch (exception: Exception) {
        onFavoriteMovieEvent.postValue(ViewModelResult.Error)
      }
    }
  }

  fun favoriteEvent(isToFavorite: Boolean) {
    movie?.let { m ->
      GlobalScope.launch {
        try {
          if (isToFavorite)
            favoriteMovieUseCase.favoriteMovie(m)
          else
            favoriteMovieUseCase.unFavoriteMovie(m)
          movie?.isFavorite = isToFavorite
          onFavoriteMovieEvent.postValue(ViewModelResult.Success)
        } catch (exception: Exception) {
          onFavoriteMovieEvent.postValue(ViewModelResult.Error)
        }
      }
    }
  }

  fun getMovie() = movie
}

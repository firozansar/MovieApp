package info.firozansari.movieapp.data

import info.firozansari.movieapp.BuildConfig
import info.firozansari.movieapp.data.api.MovieApi
import info.firozansari.movieapp.data.api.response.PageResponse
import info.firozansari.movieapp.data.db.FavoriteMoviesDAO
import info.firozansari.movieapp.domain.model.Movie
import info.firozansari.movieapp.presentation.Config

class MoviesRepository constructor(
  private val movieApi: MovieApi,
  private val favoriteDao: FavoriteMoviesDAO
) {

  suspend fun getFavoriteMovies(): List<Movie> {
    return favoriteDao.allFavoriteMovies()
  }

  suspend fun getPopularMovies(pageNumber: Int): PageResponse {
    return movieApi.getMovies(
      pageNumber = pageNumber,
      apiKey = BuildConfig.TMDB_API_KEY,
      sortBy = Config.PARAMETER_POPULAR_MOVIES
    )
  }

  suspend fun getTopRatedMovies(pageNumber: Int): PageResponse {
    return movieApi.getMovies(
      pageNumber = pageNumber,
      apiKey = BuildConfig.TMDB_API_KEY,
      sortBy = Config.PARAMETER_TOP_RATED_MOVIES
    )
  }

  suspend fun doAsFavorite(movie: Movie): Boolean {
    return try {
      favoriteDao.saveFavorite(movie.also { it.isFavorite = true })
      true
    } catch (exception: Exception) {
      false
    }
  }

  suspend fun unFavorite(movie: Movie): Boolean {
    return try {
      favoriteDao.removeFavorite(movie.id)
      true
    } catch (exception: Exception) {
      false
    }
  }

  suspend fun checkIsAFavoriteMovie(movie: Movie): Boolean {
    return favoriteDao.isThereAMovie(
      title = movie.title,
      originalTitle = movie.originalTitle
    ).isNotEmpty()
  }
}

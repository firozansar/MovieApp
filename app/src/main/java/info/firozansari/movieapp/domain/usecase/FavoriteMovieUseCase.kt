package info.firozansari.movieapp.domain.usecase

import info.firozansari.movieapp.data.MoviesRepository
import info.firozansari.movieapp.domain.model.Movie
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class FavoriteMovieUseCase @Inject constructor(private val repository: MoviesRepository) {

    suspend fun favoriteMovie(movie: Movie): Boolean {
        return withContext(Dispatchers.IO) {
            repository.doAsFavorite(movie)
        }
    }

    suspend fun unFavoriteMovie(movie: Movie): Boolean {
        return withContext(Dispatchers.IO) {
            repository.unFavorite(movie)
        }
    }

    suspend fun isFavorite(movie: Movie): Boolean {
        return withContext(Dispatchers.IO) {
            repository.checkIsAFavoriteMovie(movie)
        }
    }
}

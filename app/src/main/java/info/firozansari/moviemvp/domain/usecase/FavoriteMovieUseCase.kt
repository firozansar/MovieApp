package info.firozansari.moviemvp.domain.usecase

import info.firozansari.moviemvp.domain.model.Movie
import info.firozansari.moviemvp.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

open class FavoriteMovieUseCase(val repository: MoviesRepository) {

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
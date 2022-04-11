package info.firozansari.movieapp.domain.usecase

import info.firozansari.movieapp.data.MoviesRepository
import info.firozansari.movieapp.domain.model.Movie
import javax.inject.Inject

class FavoriteMoviesUseCase @Inject constructor(private val repository: MoviesRepository) {

    suspend fun execute(): List<Movie> {
        return repository.getFavoriteMovies()
    }
}
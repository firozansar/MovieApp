package info.firozansari.moviemvp.domain.usecase

import info.firozansari.moviemvp.domain.model.Movie
import info.firozansari.moviemvp.repository.MoviesRepository

class FavoriteMoviesUseCase(val repository: MoviesRepository) {

    suspend fun execute(): List<Movie> {
        return repository.getFavoriteMovies()
    }
}
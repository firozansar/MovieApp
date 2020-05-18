package info.firozansari.moviemvp.domain.usecase

import info.firozansari.moviemvp.domain.model.Page
import info.firozansari.moviemvp.domain.usecase.mapper.MoviesMapper
import info.firozansari.moviemvp.repository.MoviesRepository

class PopularMoviesUseCase(val repository: MoviesRepository) {

    suspend fun execute(pageNumber: Int): Page? {
        return MoviesMapper.mapToPage(repository.getPopularMovies(pageNumber))
    }
}
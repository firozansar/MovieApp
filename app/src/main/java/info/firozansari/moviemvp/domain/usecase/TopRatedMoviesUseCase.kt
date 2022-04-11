package info.firozansari.moviemvp.domain.usecase

import info.firozansari.moviemvp.domain.model.Page
import info.firozansari.moviemvp.domain.usecase.mapper.MoviesMapper
import info.firozansari.moviemvp.data.MoviesRepository

class TopRatedMoviesUseCase(val repository: MoviesRepository) {

    suspend fun execute(pageNumber: Int): Page? {
        return MoviesMapper.mapToPage(repository.getTopRatedMovies(pageNumber))
    }
}
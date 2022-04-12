package info.firozansari.movieapp.domain.usecase

import info.firozansari.movieapp.data.MoviesRepository
import info.firozansari.movieapp.domain.model.Page
import info.firozansari.movieapp.domain.usecase.mapper.MoviesMapper
import javax.inject.Inject

class PopularMoviesUseCase @Inject constructor(private val repository: MoviesRepository) {

    suspend fun execute(pageNumber: Int): Page? {
        return MoviesMapper.mapToPage(repository.getPopularMovies(pageNumber))
    }
}

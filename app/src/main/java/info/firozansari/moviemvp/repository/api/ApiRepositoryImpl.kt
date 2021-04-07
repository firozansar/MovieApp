package info.firozansari.moviemvp.repository.api

import info.firozansari.moviemvp.core.ConfigVariables
import info.firozansari.moviemvp.domain.model.Movie
import info.firozansari.moviemvp.repository.MoviesRepository
import info.firozansari.moviemvp.repository.api.response.PageResponse

class ApiRepositoryImpl(private val apiRepository: ApiRepository) : MoviesRepository {

    override suspend fun getFavoriteMovies() = emptyList<Movie>()

    override suspend fun getPopularMovies(pageNumber: Int): PageResponse {
        return apiRepository.getMovies(
            pageNumber = pageNumber,
            apiKey = ConfigVariables.TOKEN,
            sortBy = ConfigVariables.PARAMETER_POPULAR_MOVIES
        )
    }

    override suspend fun getTopRatedMovies(pageNumber: Int): PageResponse {
        return apiRepository.getMovies(
            pageNumber = pageNumber,
            apiKey = ConfigVariables.TOKEN,
            sortBy = ConfigVariables.PARAMETER_TOP_RATED_MOVIES
        )
    }

    override suspend fun doAsFavorite(movie: Movie) = false

    override suspend fun unFavorite(movie: Movie) = false

    override suspend fun checkIsAFavoriteMovie(movie: Movie) = false
}
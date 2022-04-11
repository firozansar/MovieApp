package info.firozansari.moviemvp.data.api

import info.firozansari.moviemvp.presentation.Config
import info.firozansari.moviemvp.domain.model.Movie
import info.firozansari.moviemvp.data.MoviesRepository
import info.firozansari.moviemvp.data.api.response.PageResponse

class MovieApiRepository(private val movieApi: MovieApi) : MoviesRepository {

    override suspend fun getFavoriteMovies() = emptyList<Movie>()

    override suspend fun getPopularMovies(pageNumber: Int): PageResponse {
        return movieApi.getMovies(
            pageNumber = pageNumber,
            apiKey = Config.TOKEN,
            sortBy = Config.PARAMETER_POPULAR_MOVIES
        )
    }

    override suspend fun getTopRatedMovies(pageNumber: Int): PageResponse {
        return movieApi.getMovies(
            pageNumber = pageNumber,
            apiKey = Config.TOKEN,
            sortBy = Config.PARAMETER_TOP_RATED_MOVIES
        )
    }

    override suspend fun doAsFavorite(movie: Movie) = false

    override suspend fun unFavorite(movie: Movie) = false

    override suspend fun checkIsAFavoriteMovie(movie: Movie) = false
}
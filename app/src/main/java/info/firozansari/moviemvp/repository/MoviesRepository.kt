package info.firozansari.moviemvp.repository

import info.firozansari.moviemvp.domain.model.Movie
import info.firozansari.moviemvp.repository.api.response.PageResponse

interface MoviesRepository {

    suspend fun getFavoriteMovies(): List<Movie>

    suspend fun getPopularMovies(pageNumber: Int): PageResponse?

    suspend fun getTopRatedMovies(pageNumber: Int): PageResponse?

    suspend fun doAsFavorite(movie: Movie): Boolean

    suspend fun unFavorite(movie: Movie): Boolean

    suspend fun checkIsAFavoriteMovie(movie: Movie): Boolean
}
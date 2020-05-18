package info.firozansari.moviemvp.repository.dataBase

import info.firozansari.moviemvp.domain.model.Movie
import info.firozansari.moviemvp.repository.MoviesRepository
import info.firozansari.moviemvp.repository.api.response.PageResponse

class DataBaseRepositoryImpl(private val favoriteDao: FavoriteMoviesDAO) : MoviesRepository {

    override suspend fun getFavoriteMovies(): List<Movie> {
        return favoriteDao.allFavoriteMovies()
    }

    override suspend fun getPopularMovies(pageNumber: Int): PageResponse? = null

    override suspend fun getTopRatedMovies(pageNumber: Int): PageResponse? = null

    override suspend fun doAsFavorite(movie: Movie): Boolean {
        return try {
            favoriteDao.saveFavorite(movie.also { it.isFavorite = true })
            true
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun unFavorite(movie: Movie): Boolean {
        return try {
            favoriteDao.removeFavorite(movie.id)
            true
        } catch (exception: Exception) {
            false
        }
    }

    override suspend fun checkIsAFavoriteMovie(movie: Movie): Boolean {
        return favoriteDao.isThereAMovie(
                title = movie.title,
                originalTitle = movie.originalTitle
        ).isNotEmpty()
    }

}
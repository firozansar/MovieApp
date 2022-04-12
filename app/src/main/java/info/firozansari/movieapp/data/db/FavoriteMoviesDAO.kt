package info.firozansari.movieapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import info.firozansari.movieapp.domain.model.Movie

@Dao
interface FavoriteMoviesDAO {

    @Query("SELECT * FROM  favorite_movies")
    suspend fun allFavoriteMovies(): List<Movie>

    @Query("SELECT * FROM favorite_movies WHERE title = :title and originalTitle = :originalTitle")
    suspend fun isThereAMovie(title: String, originalTitle: String): List<Movie>

    @Query("DELETE FROM favorite_movies WHERE id  = :movieId")
    suspend fun removeFavorite(movieId: Int?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavorite(movie: Movie)
}

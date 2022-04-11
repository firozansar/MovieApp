package info.firozansari.moviemvp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import info.firozansari.moviemvp.domain.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteMoviesDAO(): FavoriteMoviesDAO
}
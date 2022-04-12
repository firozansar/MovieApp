package info.firozansari.movieapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import info.firozansari.movieapp.domain.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteMoviesDAO(): FavoriteMoviesDAO
}

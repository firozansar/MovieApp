package info.firozansari.movieapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import info.firozansari.movieapp.data.db.AppDatabase
import info.firozansari.movieapp.data.db.FavoriteMoviesDAO
import info.firozansari.movieapp.presentation.Config
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, Config.DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase): FavoriteMoviesDAO {
        return appDatabase.favoriteMoviesDAO()
    }
}

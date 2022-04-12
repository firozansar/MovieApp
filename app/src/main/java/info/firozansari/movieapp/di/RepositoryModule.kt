package info.firozansari.movieapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import info.firozansari.movieapp.data.MoviesRepository
import info.firozansari.movieapp.data.api.MovieApi
import info.firozansari.movieapp.data.db.FavoriteMoviesDAO

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideMoviesRepository(
        movieApi: MovieApi,
        favoriteDao: FavoriteMoviesDAO
    ): MoviesRepository {
        return MoviesRepository(movieApi, favoriteDao)
    }
}

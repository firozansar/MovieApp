package info.firozansari.movieapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import info.firozansari.movieapp.data.api.ApiClient
import info.firozansari.movieapp.data.api.TMDBApiServiceV3
import info.firozansari.movieapp.data.api.TMDBApiServiceV4
import info.firozansari.movieapp.presentation.util.SessionPrefs
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesApiClient(): ApiClient = ApiClient()

    @Provides
    @Singleton
    fun providesTmdbV3Service(apiClient: ApiClient): TMDBApiServiceV3 =
        apiClient.retrofit.create(TMDBApiServiceV3::class.java)

    @Provides
    @Singleton
    fun providesTmdbV4Service(apiClient: ApiClient): TMDBApiServiceV4 =
        apiClient.retrofit.create(TMDBApiServiceV4::class.java)

    @Provides
    @Singleton
    fun providesSessionPrefs(@ApplicationContext context: Context): SessionPrefs =
        SessionPrefs(context)
}

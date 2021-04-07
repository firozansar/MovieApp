package info.firozansari.moviemvp.core

import androidx.room.Room
import info.firozansari.moviemvp.domain.usecase.FavoriteMovieUseCase
import info.firozansari.moviemvp.domain.usecase.FavoriteMoviesUseCase
import info.firozansari.moviemvp.domain.usecase.PopularMoviesUseCase
import info.firozansari.moviemvp.domain.usecase.TopRatedMoviesUseCase
import info.firozansari.moviemvp.presentation.MainViewModel
import info.firozansari.moviemvp.presentation.detailedScreen.MovieDetailedViewModel
import info.firozansari.moviemvp.presentation.movieList.MovieListViewModel
import info.firozansari.moviemvp.repository.MoviesRepository
import info.firozansari.moviemvp.repository.api.ApiRepository
import info.firozansari.moviemvp.repository.api.ApiRepositoryImpl
import info.firozansari.moviemvp.repository.api.LoggedInterceptor
import info.firozansari.moviemvp.repository.dataBase.DataBaseFactory
import info.firozansari.moviemvp.repository.dataBase.DataBaseRepositoryImpl
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val repositoryModule = module {
    single {
        ApiRepositoryImpl(
            Retrofit.Builder()
                .baseUrl(ConfigVariables.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .addInterceptor(LoggedInterceptor())
                        .build()
                )
                .build()
                .create(ApiRepository::class.java)
        )
    } bind MoviesRepository::class

    single {
        DataBaseRepositoryImpl(
            Room.databaseBuilder(
                androidContext(),
                DataBaseFactory::class.java,
                ConfigVariables.DATABASE_NAME
            ).build().favoriteMoviesDAO()
        )
    } bind MoviesRepository::class
}

val usecaseModule = module {
    single { TopRatedMoviesUseCase(get<ApiRepositoryImpl>()) }
    single { PopularMoviesUseCase(get<ApiRepositoryImpl>()) }
    single { FavoriteMovieUseCase(get<DataBaseRepositoryImpl>()) }
    single { FavoriteMoviesUseCase(get<DataBaseRepositoryImpl>()) }
}

val viewModelModules = module {
    viewModel { MovieListViewModel(get(), get(), get()) }
    viewModel { MovieDetailedViewModel(get()) }
    viewModel { MainViewModel() }
}
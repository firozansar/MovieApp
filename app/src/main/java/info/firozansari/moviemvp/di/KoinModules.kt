package info.firozansari.moviemvp.di

import androidx.room.Room
import info.firozansari.moviemvp.domain.usecase.FavoriteMovieUseCase
import info.firozansari.moviemvp.domain.usecase.FavoriteMoviesUseCase
import info.firozansari.moviemvp.domain.usecase.PopularMoviesUseCase
import info.firozansari.moviemvp.domain.usecase.TopRatedMoviesUseCase
import info.firozansari.moviemvp.presentation.MainViewModel
import info.firozansari.moviemvp.presentation.detailedScreen.MovieDetailedViewModel
import info.firozansari.moviemvp.presentation.movieList.MovieListViewModel
import info.firozansari.moviemvp.data.MoviesRepository
import info.firozansari.moviemvp.data.api.MovieApi
import info.firozansari.moviemvp.data.api.MovieApiRepository
import info.firozansari.moviemvp.data.api.LoggedInterceptor
import info.firozansari.moviemvp.data.db.AppDatabase
import info.firozansari.moviemvp.data.db.DataBaseRepositoryImpl
import info.firozansari.moviemvp.presentation.Config
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//val repositoryModule = module {
//    single {
//        MovieApiRepository(
//            Retrofit.Builder()
//                .baseUrl(Config.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(
//                    OkHttpClient.Builder()
//                        .connectTimeout(15, TimeUnit.SECONDS)
//                        .readTimeout(15, TimeUnit.SECONDS)
//                        .addInterceptor(LoggedInterceptor())
//                        .build()
//                )
//                .build()
//                .create(MovieApi::class.java)
//        )
//    } bind MoviesRepository::class
//
//    single {
//        DataBaseRepositoryImpl(
//            Room.databaseBuilder(
//                androidContext(),
//                AppDatabase::class.java,
//                Config.DATABASE_NAME
//            ).build().favoriteMoviesDAO()
//        )
//    } bind MoviesRepository::class
//}
//
//val usecaseModule = module {
//    single { TopRatedMoviesUseCase(get<MovieApiRepository>()) }
//    single { PopularMoviesUseCase(get<MovieApiRepository>()) }
//    single { FavoriteMovieUseCase(get<DataBaseRepositoryImpl>()) }
//    single { FavoriteMoviesUseCase(get<DataBaseRepositoryImpl>()) }
//}
//
//val viewModelModules = module {
//    viewModel { MovieListViewModel(get(), get(), get()) }
//    viewModel { MovieDetailedViewModel(get()) }
//    viewModel { MainViewModel() }
//}
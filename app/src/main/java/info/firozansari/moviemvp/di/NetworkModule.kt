package info.firozansari.moviemvp.di

import dagger.Module
import dagger.Provides
import info.firozansari.moviemvp.data.api.LoggedInterceptor
import info.firozansari.moviemvp.data.api.MovieApi
import info.firozansari.moviemvp.presentation.Config
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun okhttpClient(): OkHttpClient {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor(LoggedInterceptor())
                    .build()
            )
            .build()
    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit) =
        retrofit.create(MovieApi::class.java)

}

package info.firozansari.movieapp.data.api

import info.firozansari.movieapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    private val TMDB_BASE_URL = "https://api.themoviedb.org/"

    private val okHttpBuilder: OkHttpClient.Builder by lazy {
        OkHttpClient.Builder()
            .callTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(TMDB_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                okHttpBuilder.addInterceptor(
                    Interceptor { chain: Interceptor.Chain ->
                        val request = chain.request()
                            .newBuilder()
                            .header(
                                "Authorization",
                                "Bearer ${BuildConfig.TMDB_API_KEY}"
                            )
                            .build()
                        chain.proceed(request)
                    }
                ).build()
            ).build()
    }

    // fun <T> buildApi(api: Class<T>): T = retrofit.create(api)
}

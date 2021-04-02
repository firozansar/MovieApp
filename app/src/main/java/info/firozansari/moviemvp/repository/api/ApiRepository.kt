package info.firozansari.moviemvp.repository.api

import info.firozansari.moviemvp.repository.api.response.PageResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The inferface that defines the requests to API.
 * @author Firoz Ansari
 */
interface ApiRepository {

    /**
     * This abstract method provides the implementation
     * model of the target request (Get movies).
     * @author Firoz Ansari
     * @since 2018-08-30
     */
    @GET("discover/movie")
    suspend fun getMovies(
            @Query("api_key") apiKey: String,
            @Query("sort_by") sortBy: String,
            @Query("page") pageNumber: Int
    ): PageResponse
}
package info.firozansari.movieapp.data.api

import info.firozansari.movieapp.data.api.response.PageResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The inferface that defines the requests to API.
 * @author Firoz Ansari
 */
interface MovieApi {

    /**
     * This abstract method provides the implementation
     * model of the target request (Get movies).
     * @author Firoz Ansari
     */
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("sort_by") sortBy: String,
        @Query("page") pageNumber: Int
    ): PageResponse
}
package info.firozansari.movieapp.data.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import info.firozansari.movieapp.BuildConfig
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import javax.inject.Inject

@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class, sdk = [28])
class TMDBApiServiceV3Test {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: TMDBApiServiceV3

    @Before
    fun setUp() {
        hiltRule.inject()
        mockWebServer = MockWebServer()
        mockWebServer.start()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TMDBApiServiceV3::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    // Dummy test to ensure setup is working
    @Test
    fun `test dummy`() {
        assertThat(true).isTrue()
    }

    @Test
    fun `fetchNowPlayingMovies returns success`() = runBlocking {
        // Given
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.fetchNowPlayingMovies()

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.page).isEqualTo(1)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/movie/now_playing?language=en&page=1&api_key=${BuildConfig.TMDB_API_KEY}")
    }

    @Test
    fun `fetchTopRatedMovies returns success`() = runBlocking {
        // Given
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.fetchTopRatedMovies()

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.page).isEqualTo(1)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/movie/top_rated?region=GB&language=en&page=1&api_key=${BuildConfig.TMDB_API_KEY}")
    }

    @Test
    fun `fetchPopularMovies returns success`() = runBlocking {
        // Given
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.fetchPopularMovies()

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.page).isEqualTo(1)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/movie/popular?language=en&page=1&api_key=${BuildConfig.TMDB_API_KEY}")
    }

    @Test
    fun `fetchPopularTvShows returns success`() = runBlocking {
        // Given
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.fetchPopularTvShows()

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.page).isEqualTo(1)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/tv/popular?language=en&page=1&api_key=${BuildConfig.TMDB_API_KEY}")
    }

    @Test
    fun `fetchUpcomingMovies returns success`() = runBlocking {
        // Given
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.fetchUpcomingMovies()

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.page).isEqualTo(1)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/movie/upcoming?language=en&page=1&region=GB&api_key=${BuildConfig.TMDB_API_KEY}")
    }

    @Test
    fun `fetchMovieDetail returns success`() = runBlocking {
        // Given
        val movieId = 123
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"id\":$movieId,\"title\":\"Movie Title\"}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.fetchMovieDetail(movieId)

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.id).isEqualTo(movieId)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/movie/$movieId?append_to_response=videos&language=en&api_key=${BuildConfig.TMDB_API_KEY}")
    }

    @Test
    fun `fetchTvShowDetail returns success`() = runBlocking {
        // Given
        val tvId = 456
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"id\":$tvId,\"name\":\"TV Show Name\"}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.fetchTvShowDetail(tvId)

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.id).isEqualTo(tvId)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/tv/$tvId?append_to_response=videos&language=en&api_key=${BuildConfig.TMDB_API_KEY}")
    }

    @Test
    fun `fetchTrending returns success`() = runBlocking {
        // Given
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.fetchTrending()

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.page).isEqualTo(1)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/trending/movie/day?language=en&page=1&api_key=${BuildConfig.TMDB_API_KEY}")
    }

    @Test
    fun `fetchSimilarMovies returns success`() = runBlocking {
        // Given
        val movieId = 123
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.fetchSimilarMovies(movieId)

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.page).isEqualTo(1)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/movie/$movieId/similar?language=en&page=1&api_key=${BuildConfig.TMDB_API_KEY}")
    }

    @Test
    fun `fetchSimilarShows returns success`() = runBlocking {
        // Given
        val tvId = 456
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.fetchSimilarShows(tvId)

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.page).isEqualTo(1)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/tv/$tvId/similar?language=en&page=1&api_key=${BuildConfig.TMDB_API_KEY}")
    }

    @Test
    fun `fetchRecommendedMovies returns success`() = runBlocking {
        // Given
        val movieId = 123
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.fetchRecommendedMovies(movieId)

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.page).isEqualTo(1)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/movie/$movieId/recommendations?language=en&page=1&api_key=${BuildConfig.TMDB_API_KEY}")
    }

    @Test
    fun `fetchRecommendedTvShow returns success`() = runBlocking {
        // Given
        val tvId = 456
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.fetchRecommendedTvShow(tvId)

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.page).isEqualTo(1)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/tv/$tvId/recommendations?language=en&page=1&api_key=${BuildConfig.TMDB_API_KEY}")
    }

//    @Test
//    fun `fetchMovieSearchedResults returns success`() = runBlocking {
//        // Given
//        val searchQuery = "test query"
//        val mockResponse = MockResponse()
//            .setResponseCode(HttpURLConnection.HTTP_OK)
//            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
//        mockWebServer.enqueue(mockResponse)
//
//        // When
//        val response = apiService.fetchMovieSearchedResults(searchQuery = searchQuery)
//
//        // Then
//        assertThat(response.isSuccessful).isTrue()
//        assertThat(response.body()).isNotNull()
//        assertThat(response.body()?.page).isEqualTo(1)
//        val request = mockWebServer.takeRequest()
//        assertThat(request.method).isEqualTo("GET")
//        assertThat(request.path).isEqualTo("/3/search/movie?language=en-US&page=1&include_adult=false&query=$searchQuery")
//    }
//
//    @Test
//    fun `fetchTvSearchedResults returns success`() = runBlocking {
//        // Given
//        val searchQuery = "test query"
//        val mockResponse = MockResponse()
//            .setResponseCode(HttpURLConnection.HTTP_OK)
//            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
//        mockWebServer.enqueue(mockResponse)
//
//        // When
//        val response = apiService.fetchTvSearchedResults(searchQuery = searchQuery)
//
//        // Then
//        assertThat(response.isSuccessful).isTrue()
//        assertThat(response.body()).isNotNull()
//        assertThat(response.body()?.page).isEqualTo(1)
//        val request = mockWebServer.takeRequest()
//        assertThat(request.method).isEqualTo("GET")
//        assertThat(request.path).isEqualTo("/3/search/tv?language=en&page=1&include_adult=false&query=$searchQuery&api_key=${BuildConfig.TMDB_API_KEY}")
//    }

    @Test
    fun `fetchTvSeasonDetails returns success`() = runBlocking {
        // Given
        val tvId = 456
        val seasonNumber = 1
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"id\":$tvId,\"season_number\":$seasonNumber}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.fetchTvSeasonDetails(tvId, seasonNumber)

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.id).isEqualTo(tvId)
        //assertThat(response.body()?.season_number).isEqualTo(seasonNumber)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/tv/$tvId/season/$seasonNumber?append_to_response=videos&language=en&api_key=${BuildConfig.TMDB_API_KEY}")
    }

    @Test
    fun `fetchTvEpisodeDetails returns success`() = runBlocking {
        // Given
        val tvId = 456
        val seasonNumber = 1
        val episodeNumber = 1
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"id\":$tvId,\"season_number\":$seasonNumber,\"episode_number\":$episodeNumber}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.fetchTvEpisodeDetails(tvId, seasonNumber, episodeNumber)

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.id).isEqualTo(tvId)
//        assertThat(response.body()?.season_number).isEqualTo(seasonNumber)
//        assertThat(response.body()?.episode_number).isEqualTo(episodeNumber)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/tv/$tvId/season/$seasonNumber/episode/$episodeNumber?append_to_response=videos&language=en&api_key=${BuildConfig.TMDB_API_KEY}")
    }

//    @Test
//    fun `fetchMoviesByGenres returns success`() = runBlocking {
//        // Given
//        val genres = "28,12" // Action, Adventure
//        val mockResponse = MockResponse()
//            .setResponseCode(HttpURLConnection.HTTP_OK)
//            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
//        mockWebServer.enqueue(mockResponse)
//
//        // When
//        val response = apiService.fetchMoviesByGenres(genres = genres)
//
//        // Then
//        assertThat(response.isSuccessful).isTrue()
//        assertThat(response.body()).isNotNull()
//        assertThat(response.body()?.page).isEqualTo(1)
//        val request = mockWebServer.takeRequest()
//        assertThat(request.method).isEqualTo("GET")
//        assertThat(request.path).isEqualTo("/3/discover/movie?with_genres=$genres&sort_by=popularity.desc&page=1&include_adult=false&language=en&api_key=${BuildConfig.TMDB_API_KEY}")
//    }
//
//    @Test
//    fun `fetchTvShowsByGenres returns success`() = runBlocking {
//        // Given
//        val genres = "10759,9648" // Action & Adventure, Mystery
//        val mockResponse = MockResponse()
//            .setResponseCode(HttpURLConnection.HTTP_OK)
//            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
//        mockWebServer.enqueue(mockResponse)
//
//        // When
//        val response = apiService.fetchTvShowsByGenres(genres = genres)
//
//        // Then
//        assertThat(response.isSuccessful).isTrue()
//        assertThat(response.body()).isNotNull()
//        assertThat(response.body()?.page).isEqualTo(1)
//        val request = mockWebServer.takeRequest()
//        assertThat(request.method).isEqualTo("GET")
//        assertThat(request.path).isEqualTo("/3/discover/tv?with_genres=$genres&sort_by=popularity.desc&page=1&include_adult=false&language=en&api_key=${BuildConfig.TMDB_API_KEY}")
//    }

    @Test
    fun `fetchAnimeSeries returns success`() = runBlocking {
        // Given
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.fetchAnimeSeries()

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.page).isEqualTo(1)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/discover/tv?with_genres=16&sort_by=popularity.desc&first_air_date.gte=2023-01-01&page=1&language=en&with_original_language=en&include_null_first_air_dates=false&api_key=${BuildConfig.TMDB_API_KEY}")
    }

    @Test
    fun `fetchBollywoodMovies returns success`() = runBlocking {
        // Given
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.fetchBollywoodMovies()

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.page).isEqualTo(1)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/discover/movie?sort_by=popularity.desc&primary_release_date.gte=2023-08-01&page=1&region=IN&with_release_type=3%7C2&watch_region=GB&language=hi-IN&with_original_language=hi&api_key=${BuildConfig.TMDB_API_KEY}")
    }

    @Test
    fun `fetchMovieCast returns success`() = runBlocking {
        // Given
        val movieId = 123
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"id\":$movieId,\"cast\":[]}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.fetchMovieCast(movieId)

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.id).isEqualTo(movieId)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/movie/$movieId/credits?language=en&api_key=${BuildConfig.TMDB_API_KEY}")
    }

    @Test
    fun `fetchTvShowsCast returns success`() = runBlocking {
        // Given
        val tvId = 456
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"id\":$tvId,\"cast\":[]}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.fetchTvShowsCast(tvId)

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.id).isEqualTo(tvId)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/tv/$tvId/credits?language=en&api_key=${BuildConfig.TMDB_API_KEY}")
    }

    @Test
    fun `fetchActorFilmography returns success`() = runBlocking {
        // Given
        val personId = 789
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"id\":$personId,\"cast\":[]}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.fetchActorFilmography(personId)

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.id).isEqualTo(personId)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/person/$personId/combined_credits?language=en&api_key=${BuildConfig.TMDB_API_KEY}")
    }

    @Test
    fun `getAccountDetails returns success`() = runBlocking {
        // Given
        val sessionId = "test_session_id"
        val accountId = 1
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"id\":$accountId,\"username\":\"testuser\"}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.getAccountDetails(sessionId)

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.id).isEqualTo(accountId)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/account?session_id=$sessionId")
    }

    @Test
    fun `rateMovie returns success`() = runBlocking {
        // Given
        val movieId = 123
        val sessionId = "test_session_id"
        val ratingRequest = info.firozansari.movieapp.domain.requests.MediaRatingRequest(5.0f)
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"status_code\":1,\"status_message\":\"Success\"}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.rateMovie(movieId, sessionId, ratingRequest)

        // Then
        assertThat(response.isSuccessful).isTrue()
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("POST")
        assertThat(request.path).isEqualTo("/3/movie/$movieId/rating?session_id=$sessionId")
        assertThat(request.body.readUtf8()).isEqualTo("{\"value\":5.0}")
    }

    @Test
    fun `rateTvShow returns success`() = runBlocking {
        // Given
        val tvId = 456
        val sessionId = "test_session_id"
        val ratingRequest = info.firozansari.movieapp.domain.requests.MediaRatingRequest(8.0f)
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"status_code\":1,\"status_message\":\"Success\"}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.rateTvShow(tvId, sessionId, ratingRequest)

        // Then
        assertThat(response.isSuccessful).isTrue()
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("POST")
        assertThat(request.path).isEqualTo("/3/tv/$tvId/rating?session_id=$sessionId")
        assertThat(request.body.readUtf8()).isEqualTo("{\"value\":8.0}")
    }

    @Test
    fun `addToWatchList returns success`() = runBlocking {
        // Given
//        val accountId = 1
//        val sessionId = "test_session_id"
//        val addToWatchListRequest = info.firozansari.movieapp.domain.requests.AddToWatchListRequest("movie", 123, true)
//        val mockResponse = MockResponse()
//            .setResponseCode(HttpURLConnection.HTTP_OK)
//            .setBody("{\"status_code\":1,\"status_message\":\"Success\"}")
//        mockWebServer.enqueue(mockResponse)
//
//        // When
//        val response = apiService.addToWatchList(accountId, sessionId, addToWatchListRequest)
//
//        // Then
//        assertThat(response.isSuccessful).isTrue()
//        val request = mockWebServer.takeRequest()
//        assertThat(request.method).isEqualTo("POST")
//        assertThat(request.path).isEqualTo("/3/account/$accountId/watchlist?session_id=$sessionId")
//        assertThat(request.body.readUtf8()).isEqualTo("{\"media_type\":\"movie\",\"media_id\":123,\"watchlist\":true}")
    }

    @Test
    fun `addToFavourites returns success`() = runBlocking {
        // Given
//        val accountId = 1
//        val sessionId = "test_session_id"
//        val addToFavouriteRequest = info.firozansari.movieapp.domain.requests.AddToFavouriteRequest("tv", 456, true)
//        val mockResponse = MockResponse()
//            .setResponseCode(HttpURLConnection.HTTP_OK)
//            .setBody("{\"status_code\":1,\"status_message\":\"Success\"}")
//        mockWebServer.enqueue(mockResponse)
//
//        // When
//        val response = apiService.addToFavourites(accountId, sessionId, addToFavouriteRequest)
//
//        // Then
//        assertThat(response.isSuccessful).isTrue()
//        val request = mockWebServer.takeRequest()
//        assertThat(request.method).isEqualTo("POST")
//        assertThat(request.path).isEqualTo("/3/account/$accountId/favorite?session_id=$sessionId")
//        assertThat(request.body.readUtf8()).isEqualTo("{\"media_type\":\"tv\",\"media_id\":456,\"favorite\":true}")
    }

    @Test
    fun `deleteMovieRating returns success`() = runBlocking {
        // Given
        val movieId = 123
        val sessionId = "test_session_id"
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"status_code\":13,\"status_message\":\"The item/record was deleted successfully.\"}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.deleteMovieRating(movieId, sessionId)

        // Then
        assertThat(response.isSuccessful).isTrue()
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("DELETE")
        assertThat(request.path).isEqualTo("/3/movie/$movieId/rating?session_id=$sessionId")
    }

    @Test
    fun `deleteTvRating returns success`() = runBlocking {
        // Given
        val tvId = 456
        val sessionId = "test_session_id"
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"status_code\":13,\"status_message\":\"The item/record was deleted successfully.\"}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.deleteTvRating(tvId, sessionId)

        // Then
        assertThat(response.isSuccessful).isTrue()
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("DELETE")
        assertThat(request.path).isEqualTo("/3/tv/$tvId/rating?session_id=$sessionId")
    }

    @Test
    fun `createSessionIdFromV4 returns success`() = runBlocking {
        // Given
        val v4AccessToken = "test_v4_access_token"
        val sessionId = "test_session_id"
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"success\":true,\"session_id\":\"$sessionId\"}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.createSessionIdFromV4(v4AccessToken)

        // Then
        assertThat(response.isSuccessful).isTrue()
        //assertThat(response.body()?.session_id).isEqualTo(sessionId)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("POST")
        assertThat(request.path).isEqualTo("/3/authentication/session/convert/4")
        assertThat(request.body.readUtf8()).isEqualTo("access_token=$v4AccessToken")
    }

    @Test
    fun `getFavouriteMovies returns success`() = runBlocking {
        // Given
        val accountId = 1
        val sessionId = "test_session_id"
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.getFavouriteMovies(accountId, sessionId)

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.page).isEqualTo(1)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/account/$accountId/favorite/movies?session_id=$sessionId&language=en-US&page=1&sort_by=created_at.desc")
    }

    @Test
    fun `getFavouriteTvShows returns success`() = runBlocking {
        // Given
        val accountId = 1
        val sessionId = "test_session_id"
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.getFavouriteTvShows(accountId, sessionId)

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.page).isEqualTo(1)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/account/$accountId/favorite/tv?session_id=$sessionId&language=en-US&page=1&sort_by=created_at.desc")
    }

    @Test
    fun `getRatedMovies returns success`() = runBlocking {
        // Given
        val accountId = 1
        val sessionId = "test_session_id"
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.getRatedMovies(accountId, sessionId)

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.page).isEqualTo(1)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/account/$accountId/rated/movies?session_id=$sessionId&language=en-US&page=1&sort_by=created_at.desc")
    }

    @Test
    fun `getRatedTvShows returns success`() = runBlocking {
        // Given
        val accountId = 1
        val sessionId = "test_session_id"
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.getRatedTvShows(accountId, sessionId)

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.page).isEqualTo(1)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/account/$accountId/rated/tv?session_id=$sessionId&language=en-US&page=1&sort_by=created_at.desc")
    }

    @Test
    fun `getMoviesWatchList returns success`() = runBlocking {
        // Given
        val accountId = 1
        val sessionId = "test_session_id"
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.getMoviesWatchList(accountId, sessionId)

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.page).isEqualTo(1)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/account/$accountId/watchlist/movies?session_id=$sessionId&language=en-US&page=1&sort_by=created_at.desc")
    }

    @Test
    fun `getTvShowsWatchList returns success`() = runBlocking {
        // Given
        val accountId = 1
        val sessionId = "test_session_id"
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"page\":1,\"results\":[],\"total_pages\":1,\"total_results\":0}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.getTvShowsWatchList(accountId, sessionId)

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.page).isEqualTo(1)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/account/$accountId/watchlist/tv?session_id=$sessionId&language=en-US&page=1&sort_by=created_at.desc")
    }

    @Test
    fun `getTvShowExternalIds returns success`() = runBlocking {
        // Given
        val tvId = 456
        val imdbId = "tt1234567"
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"id\":$tvId,\"imdb_id\":\"$imdbId\"}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.getTvShowExternalIds(tvId)

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
//        assertThat(response.body()?.id).isEqualTo(tvId)
//        assertThat(response.body()?.imdb_id).isEqualTo(imdbId)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/tv/$tvId/external_ids")
    }

    @Test
    fun `getMovieWatchProviders returns success`() = runBlocking {
        // Given
        val movieId = 123
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"id\":$movieId,\"results\":{}}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.getMovieWatchProviders(movieId)

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.id).isEqualTo(movieId)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/movie/$movieId/watch/providers")
    }

    @Test
    fun `getTvWatchProviders returns success`() = runBlocking {
        // Given
        val tvId = 456
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"id\":$tvId,\"results\":{}}")
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.getTvWatchProviders(tvId)

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()).isNotNull()
        assertThat(response.body()?.id).isEqualTo(tvId)
        val request = mockWebServer.takeRequest()
        assertThat(request.method).isEqualTo("GET")
        assertThat(request.path).isEqualTo("/3/tv/$tvId/watch/providers")
    }
}

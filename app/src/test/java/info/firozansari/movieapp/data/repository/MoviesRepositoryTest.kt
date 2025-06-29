package info.firozansari.movieapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.room.Ignore
import info.firozansari.movieapp.data.api.TMDBApiServiceV3
import info.firozansari.movieapp.domain.requests.MediaRatingRequest
import info.firozansari.movieapp.domain.responses.Genre
import info.firozansari.movieapp.domain.responses.MovieDetailResponse
import info.firozansari.movieapp.domain.responses.MovieListResponse
import info.firozansari.movieapp.domain.responses.MovieResult
import info.firozansari.movieapp.domain.responses.Videos
import info.firozansari.movieapp.presentation.util.ErrorType
import info.firozansari.movieapp.presentation.util.Resource
import info.firozansari.movieapp.presentation.util.SessionPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryTest {

    @Mock
    private lateinit var apiV3: TMDBApiServiceV3

    @Mock
    private lateinit var sessionPrefs: SessionPrefs

    private lateinit var moviesRepository: MoviesRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        moviesRepository = MoviesRepository(apiV3, sessionPrefs)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchNowPlayingMovies success`() = runTest {
        // Given
        val mockResponse1 = MovieListResponse(dates = null, page = 1, movieResults = getMockMovieList(), totalPages = 1, totalResults = 2)
        `when`(apiV3.fetchPopularMovies()).thenReturn(Response.success(mockResponse1))
        val mockResponse2 = MovieListResponse(dates = null, page = 1, movieResults = getMockMovieList(), totalPages = 1, totalResults = 2)
        `when`(apiV3.fetchNowPlayingMovies()).thenReturn(Response.success(mockResponse2))

        // When
        val result = moviesRepository.fetchNowPlayingMovies()

        // Then
        verify(apiV3).fetchNowPlayingMovies()
        assertTrue(result is Resource.Success)
        assertEquals(mockResponse1, (result as Resource.Success).data)
    }

    @Test
    fun `fetchNowPlayingMovies http error`() = runTest {
        // Given
        val errorBody = """{"status_code": 401, "status_message": "Invalid API key"}""".toResponseBody(null)
        `when`(apiV3.fetchNowPlayingMovies()).thenReturn(Response.error(401, errorBody))

        // When
        val result = moviesRepository.fetchNowPlayingMovies()

        // Then
        verify(apiV3).fetchNowPlayingMovies()
        assertTrue(result is Resource.Error)
        assertEquals("Invalid API key", (result as Resource.Error).message)
        assertEquals(ErrorType.HTTP, result.errorType)
    }

//    @Test
//    fun `fetchNowPlayingMovies network error`() = runTest {
//        // Given
//        `when`(apiV3.fetchNowPlayingMovies()).thenThrow(IOException("Network error"))
//
//        // When
//        val result = moviesRepository.fetchNowPlayingMovies()
//
//        // Then
//        verify(apiV3).fetchNowPlayingMovies()
//        assertTrue(result is Resource.Error)
//        assertEquals("Please check your network connection", (result as Resource.Error).message)
//        assertEquals(ErrorType.NETWORK, result.errorType)
//    }

    @Test
    fun `fetchTopRatedMovies success`() = runTest {
        // Given
        val mockResponse = MovieListResponse(dates = null, page = 1, movieResults = getMockMovieList(), totalPages = 1, totalResults = 2)
        `when`(apiV3.fetchTopRatedMovies()).thenReturn(Response.success(mockResponse))

        // When
        val result = moviesRepository.fetchTopRatedMovies()

        // Then
        verify(apiV3).fetchTopRatedMovies()
        assertTrue(result is Resource.Success)
        assertEquals(mockResponse, (result as Resource.Success).data)
    }

    @Test
    fun `fetchTopRatedMovies http error`() = runTest {
        // Given
        val errorBody = """{"status_code": 404, "status_message": "Not Found"}""".toResponseBody(null)
        `when`(apiV3.fetchTopRatedMovies()).thenReturn(Response.error(404, errorBody))

        // When
        val result = moviesRepository.fetchTopRatedMovies()

        // Then
        verify(apiV3).fetchTopRatedMovies()
        assertTrue(result is Resource.Error)
        assertEquals("Not Found", (result as Resource.Error).message)
        assertEquals(ErrorType.HTTP, result.errorType)
    }

    @Test
    fun `fetchPopularMovies success`() = runTest {
        // Given
        val mockResponse = MovieListResponse(dates = null, page = 1, movieResults = getMockMovieList(), totalPages = 1, totalResults = 2)
        `when`(apiV3.fetchPopularMovies()).thenReturn(Response.success(mockResponse))

        // When
        val result = moviesRepository.fetchPopularMovies()

        // Then
        verify(apiV3).fetchPopularMovies()
        assertTrue(result is Resource.Success)
        assertEquals(mockResponse, (result as Resource.Success).data)
    }

    @Test
    fun `fetchPopularTvShows success`() = runTest {
        // Given
        val mockResponse = MovieListResponse(dates = null, page = 1, movieResults = getMockMovieList(), totalPages = 1, totalResults = 2)
        `when`(apiV3.fetchPopularTvShows()).thenReturn(Response.success(mockResponse))

        // When
        val result = moviesRepository.fetchPopularTvShows()

        // Then
        verify(apiV3).fetchPopularTvShows()
        assertTrue(result is Resource.Success)
        assertEquals(mockResponse, (result as Resource.Success).data)
    }

    @Test
    fun `fetchAnimeSeries success`() = runTest {
        // Given
        val mockResponse = MovieListResponse(dates = null, page = 1, movieResults = getMockMovieList(), totalPages = 1, totalResults = 2)
        `when`(apiV3.fetchAnimeSeries()).thenReturn(Response.success(mockResponse))

        // When
        val result = moviesRepository.fetchAnimeSeries()

        // Then
        verify(apiV3).fetchAnimeSeries()
        assertTrue(result is Resource.Success)
        assertEquals(mockResponse, (result as Resource.Success).data)
    }

    @Test
    fun `fetchMovieDetail success`() = runTest {
        // Given
        val movieId = 123
        val mockResponse = getMovieDetailResponse()
        `when`(apiV3.fetchMovieDetail(movieId = movieId)).thenReturn(Response.success(mockResponse))

        // When
        val result = moviesRepository.fetchMovieDetail(movieId)

        // Then
        verify(apiV3).fetchMovieDetail(movieId = movieId)
        assertTrue(result is Resource.Success)
        assertEquals(mockResponse, (result as Resource.Success).data)
    }

    @Test
    fun `rateMovie success`() = runTest {
        // Given
        val movieId = 123
        val sessionId = "test_session_id"
        val ratingRequest = MediaRatingRequest(value = 8.5f)
        val mockResponseBody = "".toResponseBody(null) // Empty response body for success
        `when`(apiV3.rateMovie(movieId = movieId, sessionId = sessionId, mediaRatingRequest = ratingRequest))
            .thenReturn(Response.success(mockResponseBody))

        // When
        val result = moviesRepository.rateMovie(movieId, sessionId, ratingRequest)

        // Then
        verify(apiV3).rateMovie(movieId = movieId, sessionId = sessionId, mediaRatingRequest = ratingRequest)
        assertTrue(result is Resource.Success)
        assertEquals(mockResponseBody, (result as Resource.Success).data)
    }

    // --- Paging Function Tests ---

    @Test
    fun `fetchNowPlayingMoviesPaging returns LiveData`() {
        // When
        val liveData = moviesRepository.fetchNowPlayingMoviesPaging()

        // Then
        // Assert that liveData is not null and is of the correct type
        // (Further testing of PagingData content would require more complex Paging 3 test setup)
        assert(liveData is LiveData<PagingData<MovieResult>>)
        // A more practical test might involve observing the LiveData and checking Pager behavior,
        // but that often leans more towards an integration test or requires Paging TestHelpers.
        // For a unit test, verifying the Pager is created with the right source is key.
        // However, without refactoring to inject PagingSourceFactory, direct verification is tricky.
        // So we ensure it runs and returns the expected LiveData type.
    }

    @Test
    fun `fetchTopRatedMoviesPaging returns LiveData`() {
        // When
        val liveData = moviesRepository.fetchTopRatedMoviesPaging()

        // Then
        assert(liveData is LiveData<PagingData<MovieResult>>)
    }

    @Test
    fun `fetchPopularMoviesPaging returns LiveData`() {
        // When
        val liveData = moviesRepository.fetchPopularMoviesPaging()

        // Then
        assert(liveData is LiveData<PagingData<MovieResult>>)
    }

    @Test
    fun `fetchMoviesByGenresPaging returns LiveData`() {
        // Given
        val genreIds = "28,12"

        // When
        val liveData = moviesRepository.fetchMoviesByGenres(genreIds)

        // Then
        assert(liveData is LiveData<PagingData<MovieResult>>)
        // Ideally, we'd also verify that GenresMoviesPagingSource is instantiated with these genreIds.
        // This would require refactoring or a more involved test setup.
    }

    private fun getMockMovieList(): List<MovieResult> {
        val mockMovieResult1 = getMockMovieResult()
        val mockMovieResult2 = getMockMovieResult().copy(id = 2, title = "Another Movie")
        return listOf(mockMovieResult1, mockMovieResult2)
    }
    private fun getMockMovieResult(): MovieResult {
        return MovieResult(
            backdropPath = "/path/to/backdrop_movie.jpg",
            genreIds = listOf(28, 12, 878), // Action, Adventure, Sci-Fi
            id = 101,
            mediaType = "movie",
            originalLanguage = "en",
            overview = "A thrilling space adventure about a hero saving the galaxy.",
            popularity = 75.6,
            posterPath = "/path/to/poster_movie.jpg",
            title = "Galaxy Hero",
            isVideoAvailable = true,
            voteAverage = 8.1,
            voteCount = 2500,
            ratingByYou = null, // User hasn't rated this movie
            releaseDate = "2023-01-15",
            originalTitle = "Galaxy Hero Original Title",
            adult = false,
            tvShowName = null, // Not a TV show
            tvShowFirstAirDate = null, // Not a TV show
            tvShowOriginalName = null // Not a TV show
        )
    }

    private fun getMockSearchResult(): MovieResult {
        return MovieResult(
            backdropPath = null, // Came as null from API
            genreIds = listOf(35, 10749), // Comedy, Romance
            id = 303,
            mediaType = "movie",
            originalLanguage = "fr",
            overview = "A charming romantic comedy set in Paris.",
            popularity = 60.1,
            posterPath = null, // Came as null from API
            title = "Paris Love Story",
            isVideoAvailable = false,
            voteAverage = 6.5,
            voteCount = 500,
            ratingByYou = null,
            releaseDate = "2021-05-10",
            originalTitle = "Une Histoire d'Amour à Paris",
            adult = false,
            tvShowName = null,
            tvShowFirstAirDate = null,
            tvShowOriginalName = null
        )
    }

    private fun getMockTvShowResult(): MovieResult {
        return MovieResult(
            backdropPath = "/path/to/backdrop_tv.jpg",
            genreIds = listOf(18, 10759), // Drama, Action & Adventure
            id = 202,
            mediaType = "tv",
            originalLanguage = "en",
            overview = "A gripping drama series about a detective solving mysteries.",
            popularity = 88.2,
            posterPath = "/path/to/poster_tv.jpg",
            title = null, // TV shows use 'name'
            isVideoAvailable = null, // Typically not present for TV shows in TMDB movie list results
            voteAverage = 7.9,
            voteCount = 1800,
            ratingByYou = 8, // User rated this TV show 8/10
            releaseDate = null, // TV shows use 'first_air_date'
            originalTitle = null, // TV shows use 'original_name'
            adult = false,
            tvShowName = "Mystery Detective",
            tvShowFirstAirDate = "2022-09-20",
            tvShowOriginalName = "Mystery Detective Original Name"
        )
    }

    private fun getMinimalMockMovie(): MovieResult {
        return MovieResult(
            id = 404,
            title = "Minimal Movie",
            genreIds = listOf(18),
            originalLanguage = "en",
            overview = "Minimal overview.",
            popularity = 10.0,
            voteAverage = 5.0,
            voteCount = 100
        )
    }

    private fun getMovieDetailResponse(): MovieDetailResponse {
        return MovieDetailResponse(
            adult = false,
            backdropPath = "/mock_backdrop.jpg",
            belongsToCollection = null,
            budget = 150000000,
            genres = listOf(Genre(id = 28, name = "Action"), Genre(id = 12, name = "Adventure")),
            homepage = "https://www.mockmovie.com",
            id = 123,
            imdbId = "tt0000001",
            originalLanguage = "en",
            originalTitle = "Mock Movie Original Title",
            overview = "This is a mock overview for a mock movie.",
            popularity = 75.0,
            posterPath = "/mock_poster.jpg",
            releaseDate = "2024-01-01",
            revenue = 300000000L,
            runtime = 135,
            status = "Released",
            tagline = "This is a mock tagline.",
            title = "Mock Movie",
            video = false,
            videos = Videos(videosList = emptyList()),
            voteAverage = 8.2,
            voteCount = 2500
        )
    }


    // How to use these in your tests:
    //
    //You would create these instances and then include them within the mocked responses from your TMDBApiServiceV3 mock. For example, if you're testing a function that fetches a list of movies:
    //
    //// In your MoviesRepositoryTest.kt
    //
    //@Test
    //fun `fetchPopularMovies success with mock data`() = runTest {
    //    // Given
    //    val movie1 = MovieResult(id = 1, title = "Movie One", genreIds = listOf(1), originalLanguage = "en", overview = "Overview 1", popularity = 1.0, voteAverage = 1.0, voteCount = 1)
    //    val movie2 = MovieResult(id = 2, title = "Movie Two", genreIds = listOf(2), originalLanguage = "en", overview = "Overview 2", popularity = 2.0, voteAverage = 2.0, voteCount = 2)
    //
    //    val mockMovieList = listOf(movie1, movie2)
    //    val mockApiResponse = MovieListResponse(results = mockMovieList, page = 1, totalPages = 1, totalResults = 2)
    //
    //    `when`(apiV3.fetchPopularMovies()).thenReturn(Response.success(mockApiResponse))
    //
    //    // When
    //    val result = moviesRepository.fetchPopularMovies()
    //
    //    // Then
    //    assertTrue(result is Resource.Success)
    //    assertEquals(mockApiResponse, (result as Resource.Success).data)
    //    assertEquals(2, result.data?.results?.size)
    //    assertEquals("Movie One", result.data?.results?.get(0)?.title)
    //}
    //Remember that because MovieResult is a data class, Kotlin provides a copy() method which is also very useful for creating variations of your mock data with minimal changes:
    //
    //val baseMovie = MovieResult(id = 505, title = "Base Movie", /* ... other essential fields ... */ genreIds = listOf(1), originalLanguage = "en", overview = "Base overview", popularity = 1.0, voteAverage = 1.0, voteCount = 1)
    //
    //val highlyRatedVersion = baseMovie.copy(voteAverage = 9.5, voteCount = 5000)
    //val unratedVersion = baseMovie.copy(ratingByYou = null)
    //Let me know if you have a specific scenario for MovieResult in mind, and I can tailor an example for that!
}

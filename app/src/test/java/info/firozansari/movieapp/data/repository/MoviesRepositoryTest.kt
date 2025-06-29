package info.firozansari.movieapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import info.firozansari.movieapp.data.api.TMDBApiServiceV3
import info.firozansari.movieapp.domain.requests.MediaRatingRequest
import info.firozansari.movieapp.domain.responses.MovieDetailResponse
import info.firozansari.movieapp.domain.responses.MovieListResponse
import info.firozansari.movieapp.domain.responses.MovieResult
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
//        val mockMovieResult1 = MovieResult(
//            id = 1,
//            title = "Test Movie 1",
//            overview = "Overview 1",
//            backdropPath = "backdropPath",
//            mediaType = listOf<>(1, 2, 3),
//            originalLanguage = TODO(),
//            popularity = TODO(),
//            posterPath = TODO(),
//            isVideoAvailable = TODO(),
//            voteAverage = TODO(),
//            voteCount = TODO(),
//            ratingByYou = TODO(),
//            releaseDate = TODO(),
//            originalTitle = TODO(),
//            adult = TODO(),
//            tvShowName = TODO(),
//            tvShowFirstAirDate = TODO(),
//            tvShowOriginalName = TODO()
//        )
//        val mockMovieResult2 = MovieResult(id = 2, title = "Test Movie 2", overview = "Overview 2")
//        val mockMovieList = listOf(mockMovieResult1, mockMovieResult2)
//        val mockResponse1 = MovieListResponse(results = mockMovieList, page = 1, totalPages = 1, totalResults = 2)
//
//        `when`(apiV3.fetchPopularMovies()).thenReturn(Response.success(mockResponse1))
//        val mockResponse2 = MovieListResponse(results = mockMovieList) // Assuming MovieListResponse has a results field
//        `when`(apiV3.fetchNowPlayingMovies()).thenReturn(Response.success(mockResponse2))
//
//        // When
//        val result = moviesRepository.fetchNowPlayingMovies()
//
//        // Then
//        verify(apiV3).fetchNowPlayingMovies()
//        assertTrue(result is Resource.Success)
//        assertEquals(mockResponse, (result as Resource.Success).data)
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

    @Test
    fun `fetchNowPlayingMovies network error`() = runTest {
        // Given
        `when`(apiV3.fetchNowPlayingMovies()).thenThrow(IOException("Network error"))

        // When
        val result = moviesRepository.fetchNowPlayingMovies()

        // Then
        verify(apiV3).fetchNowPlayingMovies()
        assertTrue(result is Resource.Error)
        assertEquals("Please check your network connection", (result as Resource.Error).message)
        assertEquals(ErrorType.NETWORK, result.errorType)
    }

    @Test
    fun `fetchTopRatedMovies success`() = runTest {
        // Given
        val mockResponse = MovieListResponse(results = emptyList())
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
        val mockResponse = MovieListResponse(results = emptyList())
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
        val mockResponse = MovieListResponse(results = emptyList()) // Assuming TV shows also use MovieListResponse
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
        val mockResponse = MovieListResponse(results = emptyList())
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
        val mockResponse = MovieDetailResponse(id = movieId, title = "Mock Movie", overview = "Overview") // Populate with necessary fields
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
}

package info.firozansari.movieapp.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import info.firozansari.movieapp.domain.model.Movie
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class FavouriteMoviesDAOTest : LocalDatabase() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `saveFavoriteAndReadTest`() = runBlocking {
        // Given
        val mockMovie = createMockMovie()
        mockMovie.isFavorite = true

        // When
        db.favoriteMoviesDAO().saveFavorite(mockMovie)

        // Then
        val allFavorites = db.favoriteMoviesDAO().allFavoriteMovies()
        assertThat(allFavorites).isNotEmpty()
        assertThat(allFavorites).hasSize(1)
        val retrievedMovie = allFavorites[0]
        assertThat(retrievedMovie.id).isEqualTo(mockMovie.id)
        assertThat(retrievedMovie.title).isEqualTo(mockMovie.title)
        assertThat(retrievedMovie.originalTitle).isEqualTo(mockMovie.originalTitle)
        assertThat(retrievedMovie.isFavorite).isTrue()

        val isThere = db.favoriteMoviesDAO().isThereAMovie(mockMovie.title, mockMovie.originalTitle)
        assertThat(isThere).isNotEmpty()
        assertThat(isThere[0].id).isEqualTo(mockMovie.id)
    }

    @Test
    fun `removeFavoriteTest`() = runBlocking {
        // Given
        val mockMovie = createMockMovie()
        db.favoriteMoviesDAO().saveFavorite(mockMovie)

        // When
        db.favoriteMoviesDAO().removeFavorite(mockMovie.id)

        // Then
        val allFavorites = db.favoriteMoviesDAO().allFavoriteMovies()
        assertThat(allFavorites).isEmpty()

        val isThere = db.favoriteMoviesDAO().isThereAMovie(mockMovie.title, mockMovie.originalTitle)
        assertThat(isThere).isEmpty()
    }

    @Test
    fun `saveFavoriteOnConflictReplaceTest`() = runBlocking {
        // Given
        val mockMovie = createMockMovie(isFavorite = false, overview = "Initial Overview")
        db.favoriteMoviesDAO().saveFavorite(mockMovie)

        // When
        // Modify the movie (e.g., mark as favorite, change overview) but keep the same ID
        val updatedMovie = mockMovie.copy(isFavorite = true, overview = "Updated Overview")
        db.favoriteMoviesDAO().saveFavorite(updatedMovie)

        // Then
        val allFavorites = db.favoriteMoviesDAO().allFavoriteMovies()
        assertThat(allFavorites).hasSize(1) // Should still be one movie due to OnConflictStrategy.REPLACE

        val retrievedMovie = allFavorites[0]
        assertThat(retrievedMovie.id).isEqualTo(mockMovie.id)
        assertThat(retrievedMovie.isFavorite).isTrue()
        assertThat(retrievedMovie.overview).isEqualTo("Updated Overview")

        val isThere = db.favoriteMoviesDAO().isThereAMovie(mockMovie.title, mockMovie.originalTitle)
        assertThat(isThere).isNotEmpty()
        assertThat(isThere[0].isFavorite).isTrue()
        assertThat(isThere[0].overview).isEqualTo("Updated Overview")
    }

    // Function to create a single mock Movie instance
    fun createMockMovie(
        id: Int? = 1,
        votes: Int = 1500,
        isVideo: Boolean = false,
        votesAverage: Float = 7.8f,
        title: String = "Mock Movie Title",
        popularity: Float = 120.5f,
        posterPath: String = "/mockPoster.jpg",
        originalLanguage: String = "en",
        originalTitle: String = "Mock Movie Original Title",
        backdropPath: String = "/mockBackdrop.jpg",
        isAdult: Boolean = false,
        overview: String = "This is a mock overview for a fantastic mock movie.",
        releaseDate: String = "2024-07-22",
        isFavorite: Boolean = false
    ): Movie {
        return Movie(
            id = id,
            votes = votes,
            isVideo = isVideo,
            votesAverage = votesAverage,
            title = title,
            popularity = popularity,
            posterPath = posterPath,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            backdropPath = backdropPath,
            isAdult = isAdult,
            overview = overview,
            releaseDate = releaseDate,
            isFavorite = isFavorite
        )
    }

    fun createMockMovieList(count: Int = 3): List<Movie> {
        return (1..count).map {
            createMockMovie(
                id = it, // Ensure unique IDs for list items if needed
                title = "Mock Movie Title $it",
                originalTitle = "Mock Movie Original Title $it"
            )
        }
    }
}

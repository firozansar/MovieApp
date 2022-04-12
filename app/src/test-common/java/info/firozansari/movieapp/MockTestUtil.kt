package info.firozansari.movieapp

import info.firozansari.movieapp.domain.model.Movie

class MockTestUtil {
    companion object {
        fun mockMovieList(): List<Movie> {
            val movies = listOf(
                Movie(
                    id = 634649,
                    votes = 11264,
                    isVideo = false,
                    votesAverage = 8.2F,
                    title = "Spider-Man: No Way Home",
                    popularity = 7848.875F,
                    posterPath = "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
                    originalLanguage = "en",
                    originalTitle = "Spider-Man: No Way Home",
                    backdropPath = "/iQFcwSGbZXMkeyKrxbPnwnRo5fl.jpg",
                    isAdult = false,
                    overview = "Peter Parker is unmasked and no longer able to separate his normal life from " +
                            "the high-stakes of being a super-hero. When he asks for help from Doctor Strange the " +
                            "stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
                    releaseDate = "2021-12-15",
                    isFavorite = false
                )
            )
            return movies
        }
    }
}

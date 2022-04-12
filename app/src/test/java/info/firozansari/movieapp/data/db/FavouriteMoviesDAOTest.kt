package info.firozansari.movieapp.data.db

import info.firozansari.movieapp.MockTestUtil.Companion.mockMovieList
import org.junit.Test

class FavouriteMoviesDAOTest : LocalDatabase() {
    @Test
    fun insertAndReadTest() {
        val mockData = mockMovieList()
//        db.favoriteMoviesDAO().saveFavorite(mockData[0])
//        val loadFromDB = db.movieDao().getMovieList(1)[0]
//        MatcherAssert.assertThat(loadFromDB.page, CoreMatchers.`is`(1))
//        MatcherAssert.assertThat(loadFromDB.id, CoreMatchers.`is`(123))
    }

    @Test
    fun updateAndReadTest() {
        val mockData = mockMovieList()
//        val movie = mockMovie()
//        db.movieDao().insertMovieList(mockData)
//
//        val loadFromDB = db.movieDao().getMovie(movie.id)
//        MatcherAssert.assertThat(loadFromDB.page, CoreMatchers.`is`(1))
//
//        movie.page = 10
//        db.movieDao().updateMovie(movie)
//
//        val updated = db.movieDao().getMovie(movie.id)
//        MatcherAssert.assertThat(updated.page, CoreMatchers.`is`(10))
    }
}

package info.firozansari.movieapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import info.firozansari.movieapp.data.api.TMDBApiServiceV3
import info.firozansari.movieapp.data.paging.AnimePagingSource
import info.firozansari.movieapp.data.paging.BollywoodPagingSource
import info.firozansari.movieapp.data.paging.GenresMoviesPagingSource
import info.firozansari.movieapp.data.paging.GenresTvShowsPagingSource
import info.firozansari.movieapp.data.paging.NowPlayingPagingSource
import info.firozansari.movieapp.data.paging.PopularMoviesPagingSource
import info.firozansari.movieapp.data.paging.PopularTvPagingSource
import info.firozansari.movieapp.data.paging.TopRatedPagingSource
import info.firozansari.movieapp.data.paging.TrendingMediaPagingSource
import info.firozansari.movieapp.domain.requests.AddToFavouriteRequest
import info.firozansari.movieapp.domain.model.requests.AddToWatchListRequest
import info.firozansari.movieapp.domain.model.requests.MediaRatingRequest
import info.firozansari.movieapp.domain.responses.MovieResult
import info.firozansari.movieapp.presentation.Config.MOVIE
import info.firozansari.movieapp.presentation.Config.TV
import info.firozansari.movieapp.presentation.util.SessionPrefs
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val apiV3: TMDBApiServiceV3,
    private val sessionPrefs: SessionPrefs
) : BaseRepository(sessionPrefs) {

    suspend fun fetchNowPlayingMovies() = safeApiCall { apiV3.fetchNowPlayingMovies() }

    suspend fun fetchTopRatedMovies() = safeApiCall { apiV3.fetchTopRatedMovies() }

    suspend fun fetchPopularMovies() = safeApiCall { apiV3.fetchPopularMovies() }

    suspend fun fetchPopularTvShows() = safeApiCall { apiV3.fetchPopularTvShows() }

    suspend fun fetchAnimeSeries() = safeApiCall { apiV3.fetchAnimeSeries() }

    suspend fun fetchBollywoodMovies() = safeApiCall { apiV3.fetchBollywoodMovies() }

    fun fetchNowPlayingMoviesPaging(): LiveData<PagingData<MovieResult>> {
        return Pager<Int, MovieResult>(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                NowPlayingPagingSource(apiV3)
            }
        ).liveData
    }

    fun fetchTopRatedMoviesPaging(): LiveData<PagingData<MovieResult>> {
        return Pager<Int, MovieResult>(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                TopRatedPagingSource(apiV3)
            }
        ).liveData
    }

    fun fetchPopularMoviesPaging(): LiveData<PagingData<MovieResult>> {
        return Pager<Int, MovieResult>(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                PopularMoviesPagingSource(apiV3)
            }
        ).liveData
    }

    fun fetchPopularTvShowsPaging(): LiveData<PagingData<MovieResult>> {
        return Pager<Int, MovieResult>(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                PopularTvPagingSource(apiV3)
            }
        ).liveData
    }

    fun fetchAnimeSeriesPaging(): LiveData<PagingData<MovieResult>> {
        return Pager<Int, MovieResult>(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                AnimePagingSource(apiV3)
            }
        ).liveData
    }

    fun fetchBollywoodMoviesPaging(): LiveData<PagingData<MovieResult>> {
        return Pager<Int, MovieResult>(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                BollywoodPagingSource(apiV3)
            }
        ).liveData
    }

    suspend fun fetchUpcomingMovies() = safeApiCall {
        apiV3.fetchUpcomingMovies()
    }

    suspend fun fetchTrendingMovies() = safeApiCall {
        apiV3.fetchTrending(mediaType = "movie", timeWindow = "week")
    }

    fun fetchTrendingMoviesPaging(): LiveData<PagingData<MovieResult>> {
        return Pager<Int, MovieResult>(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                TrendingMediaPagingSource(api = apiV3, mediaType = MOVIE)
            }
        ).liveData
    }

    fun fetchTrendingTvShowsPaging(): LiveData<PagingData<MovieResult>> {
        return Pager<Int, MovieResult>(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                TrendingMediaPagingSource(api = apiV3, mediaType = TV)
            }
        ).liveData
    }

    suspend fun fetchTrendingTvShows() = safeApiCall {
        apiV3.fetchTrending(mediaType = "tv", timeWindow = "week")
    }

    suspend fun fetchSearchedMovies(searchQuery: String) = safeApiCall {
        apiV3.fetchMovieSearchedResults(searchQuery = searchQuery)
    }

    suspend fun fetchSearchedTvShows(searchQuery: String) = safeApiCall {
        apiV3.fetchTvSearchedResults(searchQuery = searchQuery)
    }

    suspend fun fetchMovieDetail(movieId: Int) = safeApiCall {
        apiV3.fetchMovieDetail(movieId = movieId)
    }

    suspend fun fetchTvShowDetail(tvId: Int) = safeApiCall {
        apiV3.fetchTvShowDetail(tvId = tvId)
    }

    suspend fun fetchSimilarMovies(movieId: Int) = safeApiCall {
        apiV3.fetchSimilarMovies(movieId = movieId)
    }

    suspend fun fetchSimilarShows(tvID: Int) = safeApiCall {
        apiV3.fetchSimilarShows(tvId = tvID)
    }

    suspend fun fetchRecommendedMovies(movieId: Int) = safeApiCall {
        apiV3.fetchRecommendedMovies(movieId = movieId)
    }

    suspend fun fetchRecommendedTvShows(tvID: Int) = safeApiCall {
        apiV3.fetchRecommendedTvShow(tvId = tvID)
    }

    suspend fun fetchTvSeasonDetails(tvId: Int, seasonNumber: Int) = safeApiCall {
        apiV3.fetchTvSeasonDetails(tvId = tvId, seasonNumber)
    }

    suspend fun fetchTvEpisodeDetails(
        tvId: Int,
        seasonNumber: Int,
        episodeNumber: Int
    ) = safeApiCall {
        apiV3.fetchTvEpisodeDetails(tvId = tvId, seasonNumber, episodeNumber)
    }

    fun fetchMoviesByGenres(genresIds: String): LiveData<PagingData<MovieResult>> {
        return Pager<Int, MovieResult>(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                GenresMoviesPagingSource(api = apiV3, genresIds = genresIds)
            }
        ).liveData
    }

    fun fetchTvShowsByGenres(genresIds: String): LiveData<PagingData<MovieResult>> {
        return Pager<Int, MovieResult>(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                GenresTvShowsPagingSource(api = apiV3, genresIds = genresIds)
            }
        ).liveData
    }

    suspend fun fetchMovieCast(movieId: Int) = safeApiCall {
        apiV3.fetchMovieCast(movieId = movieId)
    }

    suspend fun fetchTvShowCast(tvId: Int) = safeApiCall {
        apiV3.fetchTvShowsCast(tvId = tvId)
    }

    suspend fun fetchActorsMoviesShows(personId: Int) = safeApiCall {
        apiV3.fetchActorFilmography(personId = personId)
    }

    suspend fun rateMovie(
        movieId: Int,
        sessionId: String,
        mediaRatingRequest: MediaRatingRequest
    ) = safeApiCall {
        apiV3.rateMovie(
            movieId = movieId,
            sessionId = sessionId,
            mediaRatingRequest = mediaRatingRequest
        )
    }

    suspend fun rateTvShow(
        tvId: Int,
        sessionId: String,
        mediaRatingRequest: MediaRatingRequest
    ) = safeApiCall {
        apiV3.rateTvShow(
            tvId = tvId,
            sessionId = sessionId,
            mediaRatingRequest = mediaRatingRequest
        )
    }

    suspend fun addToWatchList(
        accountId: Int,
        sessionId: String,
        addToWatchListRequest: AddToWatchListRequest
    ) = safeApiCall {
        apiV3.addToWatchList(accountId, sessionId, addToWatchListRequest)
    }

    suspend fun addToFavourites(
        accountId: Int,
        sessionId: String,
        addToFavouriteRequest: AddToFavouriteRequest
    ) = safeApiCall {
        apiV3.addToFavourites(accountId, sessionId, addToFavouriteRequest)
    }

    suspend fun deleteRating(
        mediaId: Int,
        isMovie: Boolean,
        sessionId: String
    ) = safeApiCall {
        if (isMovie)
            apiV3.deleteMovieRating(movieId = mediaId, sessionId = sessionId)
        else
            apiV3.deleteTvRating(tvId = mediaId, sessionId = sessionId)
    }

    suspend fun getTvShowExternalIds(tvId: Int) = safeApiCall {
        apiV3.getTvShowExternalIds(tvId)
    }

    suspend fun getMovieWatchProviders(
        movieId: Int,
    ) = safeApiCall {
        apiV3.getMovieWatchProviders(movieId)
    }

    suspend fun getTvWatchProviders(
        tvId: Int,
    ) = safeApiCall {
        apiV3.getTvWatchProviders(tvId)
    }
}

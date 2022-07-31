package info.firozansari.movieapp.data.repository

import info.firozansari.movieapp.data.api.TMDBApiServiceV3
import info.firozansari.movieapp.data.api.TMDBApiServiceV4
import info.firozansari.movieapp.domain.requests.RequestToken
import info.firozansari.movieapp.domain.responses.AccessTokenResponse
import info.firozansari.movieapp.domain.responses.MovieListResponse
import info.firozansari.movieapp.domain.responses.RequestTokenResponse
import info.firozansari.movieapp.domain.responses.SessionResponse
import info.firozansari.movieapp.presentation.Config.MOVIE_APP_URL
import info.firozansari.movieapp.presentation.util.Resource
import info.firozansari.movieapp.presentation.util.SessionPrefs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiV4: TMDBApiServiceV4,
    private val apiV3: TMDBApiServiceV3,
    private val sessionPrefs: SessionPrefs
) : BaseRepository(sessionPrefs) {

    suspend fun requestToken(): Resource<RequestTokenResponse> = safeApiCall {
        apiV4.requestToken(MOVIE_APP_URL)
    }

    suspend fun requestUserAccessToken(
        requestToken: String
    ): Resource<AccessTokenResponse> = safeApiCall {
        apiV4.requestAccessToken(RequestToken(requestToken))
    }

    suspend fun createSession(
        accessToken: String
    ): Resource<SessionResponse> = safeApiCall {
        apiV3.createSessionIdFromV4(v4AccessToken = accessToken)
    }

    suspend fun getUserDetail(sessionId: String) = safeApiCall {
        apiV3.getAccountDetails(sessionId = sessionId)
    }

    fun getFavouriteMovies(
        accountId: Int,
        sessionId: String
    ): Flow<Response<MovieListResponse>> = flow<Response<MovieListResponse>> {
        emit(apiV3.getFavouriteMovies(accountId, sessionId))
    }

    fun getFavouriteTvShows(
        accountId: Int,
        sessionId: String
    ): Flow<Response<MovieListResponse>> = flow {
        emit(apiV3.getFavouriteTvShows(accountId = accountId, sessionId = sessionId))
    }

    fun getMoviesWatchList(
        accountId: Int,
        sessionId: String
    ): Flow<Response<MovieListResponse>> = flow {
        emit(apiV3.getMoviesWatchList(accountId = accountId, sessionId = sessionId))
    }

    fun getTvShowsWatchList(
        accountId: Int,
        sessionId: String
    ): Flow<Response<MovieListResponse>> = flow {
        emit(apiV3.getTvShowsWatchList(accountId = accountId, sessionId = sessionId))
    }

    fun getRatedMovies(
        accountId: Int,
        sessionId: String
    ): Flow<Response<MovieListResponse>> = flow {
        emit(apiV3.getRatedMovies(accountId = accountId, sessionId = sessionId))
    }

    fun getRatedTvShows(
        accountId: Int,
        sessionId: String
    ): Flow<Response<MovieListResponse>> = flow {
        emit(apiV3.getRatedTvShows(accountId = accountId, sessionId = sessionId))
    }
}

package info.firozansari.movieapp.data.api

import info.firozansari.movieapp.domain.requests.RequestToken
import info.firozansari.movieapp.domain.responses.AccessTokenResponse
import info.firozansari.movieapp.domain.responses.RequestTokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

// TMDB API Version - 4
interface TMDBApiServiceV4 {

    @POST("4/auth/request_token")
    @FormUrlEncoded
    suspend fun requestToken(
        @Field("redirect_to") redirectTo: String
    ): Response<RequestTokenResponse>

    // Create access token (successfully returns access token if and only if user has approved request token)
    @POST("4/auth/access_token")
    suspend fun requestAccessToken(
        @Body requestToken: RequestToken
    ): Response<AccessTokenResponse>
}

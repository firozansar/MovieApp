package info.firozansari.movieapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import info.firozansari.movieapp.data.repository.MoviesRepository
import info.firozansari.movieapp.data.api.response.HomeFeed
import info.firozansari.movieapp.data.api.response.HomeFeedData
import info.firozansari.movieapp.domain.model.requests.AddToWatchListRequest
import info.firozansari.movieapp.domain.model.responses.MovieListResponse
import info.firozansari.movieapp.domain.model.responses.TmdbErrorResponse
import info.firozansari.movieapp.presentation.BaseViewModel
import info.firozansari.movieapp.presentation.Config.ANIME_SERIES
import info.firozansari.movieapp.presentation.Config.BOLLYWOOD_MOVIES
import info.firozansari.movieapp.presentation.Config.NEWLY_LAUNCHED
import info.firozansari.movieapp.presentation.Config.POPULAR_MOVIES
import info.firozansari.movieapp.presentation.Config.POPULAR_TV_SHOWS
import info.firozansari.movieapp.presentation.Config.TOP_RATED_MOVIES
import info.firozansari.movieapp.presentation.util.ErrorType
import info.firozansari.movieapp.presentation.util.Resource
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepo: MoviesRepository
) : BaseViewModel(movieRepo) {

    private var _allFeedList: MutableLiveData<Resource<HomeFeedData>> = MutableLiveData()
    var allFeedList: LiveData<Resource<HomeFeedData>> = _allFeedList

    init {
        fetchAllFeedLists()
    }

    fun retry() = fetchAllFeedLists()

    private fun fetchAllFeedLists() = viewModelScope.launch {
        _allFeedList.postValue(Resource.Loading())
        try {
            coroutineScope {
                val nowPlayingMoviesListDef: Deferred<Resource<MovieListResponse>> =
                    async { movieRepo.fetchNowPlayingMovies() }
                val popularMoviesListDef = async { movieRepo.fetchPopularMovies() }
                val popularTvListDef = async { movieRepo.fetchPopularTvShows() }
                val topRatedMoviesListDef = async { movieRepo.fetchTopRatedMovies() }
                val animeSeriesDef = async { movieRepo.fetchAnimeSeries() }
                val bollywoodDef = async { movieRepo.fetchBollywoodMovies() }

                val wholeList = mutableListOf<HomeFeed>()

                // Now playing
                val nowPlayingMoviesList: Resource<MovieListResponse> =
                    nowPlayingMoviesListDef.await()
                // Popular Movies
                val popularMoviesList = popularMoviesListDef.await()
                // Popular Tv
                val popularTvList = popularTvListDef.await()
                // Top Rated
                val topRatedMoviesList = topRatedMoviesListDef.await()
                // Anime Series
                val animeSeriesList = animeSeriesDef.await()
                // Bollywood
                val bollywoodList = bollywoodDef.await()

                wholeList.add(HomeFeed(NEWLY_LAUNCHED, nowPlayingMoviesList.data!!.movieResults))
                wholeList.add(HomeFeed(POPULAR_MOVIES, popularMoviesList.data!!.movieResults))
                wholeList.add(HomeFeed(POPULAR_TV_SHOWS, popularTvList.data!!.movieResults))
                wholeList.add(HomeFeed(TOP_RATED_MOVIES, topRatedMoviesList.data!!.movieResults))
                wholeList.add(HomeFeed(ANIME_SERIES, animeSeriesList.data!!.movieResults))
                wholeList.add(HomeFeed(BOLLYWOOD_MOVIES, bollywoodList.data!!.movieResults))

                _allFeedList.postValue(
                    Resource.Success(
                        data = HomeFeedData(
                            bannerMovie = nowPlayingMoviesList.data.movieResults[
                                Random.nextInt(
                                    0,
                                    9
                                )
                            ],
                            homeFeedList = wholeList
                        )
                    )
                )
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> {
                    // val code = e.code() HTTP Exception code
                    val errorResponse: TmdbErrorResponse? = convertErrorBody(throwable)
                    _allFeedList.postValue(
                        Resource.Error(
                            errorMessage = errorResponse?.statusMessage ?: "Something went wrong",
                            errorType = ErrorType.HTTP
                        )
                    )
                }
                is IOException -> {
                    _allFeedList.postValue(
                        Resource.Error(
                            "Please check your internet connection",
                            errorType = ErrorType.NETWORK
                        )
                    )
                }
                else -> {
                    _allFeedList.postValue(
                        Resource.Error(
                            errorMessage = throwable.message ?: "Something went wrong",
                            errorType = ErrorType.UNKNOWN
                        )
                    )
                }
            }
        }
    }

    private fun convertErrorBody(throwable: Throwable): TmdbErrorResponse? {
        return try {
            (throwable as HttpException).response()?.errorBody()?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(TmdbErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }

    suspend fun addToWatchList(
        accountId: Int,
        sessionId: String,
        addToWatchListRequest: AddToWatchListRequest
    ): Resource<ResponseBody> {
        return movieRepo.addToWatchList(accountId, sessionId, addToWatchListRequest)
    }
}

package info.firozansari.movieapp.data.api.response

import info.firozansari.movieapp.domain.responses.MovieResult

data class HomeFeedData(
    val bannerMovie: MovieResult,
    val homeFeedList: List<HomeFeed>
)

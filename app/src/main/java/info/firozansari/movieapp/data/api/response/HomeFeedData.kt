package info.firozansari.movieapp.data.api.response

import info.firozansari.movieapp.domain.model.responses.MovieResult

data class HomeFeedData(
    val bannerMovie: MovieResult,
    val homeFeedList: List<HomeFeed>
)

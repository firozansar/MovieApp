package info.firozansari.movieapp.data.api.response

import info.firozansari.movieapp.domain.responses.MovieResult

data class HomeFeed(
    val title: String,
    val list: List<MovieResult>
)

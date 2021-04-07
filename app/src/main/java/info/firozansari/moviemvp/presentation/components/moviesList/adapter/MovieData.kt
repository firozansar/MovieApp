package info.firozansari.moviemvp.presentation.components.moviesList.adapter

import info.firozansari.moviemvp.domain.model.Movie

data class MovieData(
    val posterPath: String,
    val movieTitle: String,
    val releaseDate: String,
    val votes: Float,
    var movieReference: Movie
)
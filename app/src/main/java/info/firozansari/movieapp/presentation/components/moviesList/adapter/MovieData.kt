package info.firozansari.movieapp.presentation.components.moviesList.adapter

import info.firozansari.movieapp.domain.model.Movie

data class MovieData(
  val posterPath: String,
  val movieTitle: String,
  val releaseDate: String,
  val votes: Float,
  var movieReference: Movie
)

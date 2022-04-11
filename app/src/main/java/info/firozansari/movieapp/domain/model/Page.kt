package info.firozansari.movieapp.domain.model

data class Page(
  val movies: List<Movie>,
  val hasMorePages: Boolean
)

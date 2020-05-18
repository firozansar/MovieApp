package info.firozansari.moviemvp.domain.model

data class Page(
        val movies: List<Movie>,
        val hasMorePages: Boolean
)
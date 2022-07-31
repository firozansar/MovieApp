package info.firozansari.movieapp.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import info.firozansari.movieapp.databinding.ItemTopMovieBinding
import info.firozansari.movieapp.domain.responses.MovieResult
import info.firozansari.movieapp.presentation.Config.TMDB_IMAGE_BASE_URL_W500

class TopSearchesAdapter(
    private var onMovieClick: (movieResult: MovieResult) -> Unit
) : ListAdapter<MovieResult, TopSearchesAdapter.ViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemTopMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemTopMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movieResult: MovieResult) = binding.apply {
            movieImage.load(TMDB_IMAGE_BASE_URL_W500.plus(movieResult.backdropPath))
            movieNameText.text = movieResult.title ?: movieResult.tvShowName
            root.setOnClickListener {
                onMovieClick(movieResult)
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<MovieResult>() {

        override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean =
            oldItem.equals(newItem)
    }
}

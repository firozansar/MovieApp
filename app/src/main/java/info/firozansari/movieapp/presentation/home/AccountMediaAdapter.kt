package info.firozansari.movieapp.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import info.firozansari.movieapp.databinding.ItemAccountMediaPosterBinding
import com.google.android.material.snackbar.Snackbar
import info.firozansari.movieapp.domain.responses.MovieResult
import info.firozansari.movieapp.presentation.Config.TMDB_POSTER_IMAGE_BASE_URL_W342
import info.firozansari.movieapp.presentation.util.showSnackBar

class AccountMediaAdapter(
    private var onPosterClick: ((movieResult: MovieResult) -> Unit)? = null,
    private var onDeleteClick: ((movieResult: MovieResult, deleteCallback: (state: DeletedState) -> Unit) -> Unit)? = null,
    private var showRated: Boolean = false
) : ListAdapter<MovieResult, AccountMediaAdapter.ViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemAccountMediaPosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class ViewHolder(
        private val binding: ItemAccountMediaPosterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movieResult: MovieResult, position: Int) = binding.apply {
            posterImage.load(TMDB_POSTER_IMAGE_BASE_URL_W342.plus(movieResult.posterPath))
            linRate.isGone = !showRated
            posterImage.setOnClickListener {
                onPosterClick?.invoke(movieResult)
            }
            imgDelete.setOnClickListener {
                onDeleteClick?.invoke(movieResult) { state ->
                    // Deleted callback
                    if (state.isLoading) {
                        imgDelete.isEnabled = false
                        mainLayout.alpha = 0.45f
                        progressBar.isGone = false
                    } else {
                        mainLayout.alpha = 1f
                        progressBar.isGone = true
                        this.root.showSnackBar(
                            if (state.isSuccess) "${movieResult.title ?: movieResult.tvShowName} removed" else state.errorMessage,
                            length = Snackbar.LENGTH_SHORT
                        )
                        imgDelete.isEnabled = true
                    }
                }
            }
            movieResult.ratingByYou?.let {
                yourRatingText.text = "$it"
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

data class DeletedState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String = ""
)

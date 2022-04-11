package info.firozansari.movieapp.presentation.components.moviesList.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import info.firozansari.movieapp.R
import info.firozansari.movieapp.domain.model.Movie
import info.firozansari.movieapp.presentation.components.FiveStarsComponent
import info.firozansari.movieapp.presentation.detailedScreen.MovieDetailedActivity
import info.firozansari.movieapp.presentation.util.setImagePath

class MoviesListAdapter : RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>() {

  private val elements = ArrayList<MovieData>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
    return MovieViewHolder(
      LayoutInflater.from(parent.context)
        .inflate(R.layout.view_holder_movie_card, parent, false)
    )
  }

  override fun getItemCount() = elements.size

  override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    try {
      elements[position]
    } catch (indexOutOfBounds: IndexOutOfBoundsException) {
      null
    }?.let { data ->
      holder.bind(data)
    }
  }

  override fun getItemViewType(position: Int): Int {
    return VIEW_TYPE
  }

  fun setup(movieDataList: List<Movie>) {
    elements.clear()
    elements.addAll(
      movieDataList.mapToElements()
    )
    notifyDataSetChanged()
  }

  fun update(newElements: List<Movie>) {
    val previousSize = elements.size
    elements.addAll(
      newElements.mapToElements()
    )
    notifyItemRangeInserted(previousSize, newElements.size)
  }

  inner class MovieViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val ivPoster: ImageView? = view.findViewById(R.id.ivPoster)
    private val tvTitle: TextView? = view.findViewById(R.id.tvTitle)
    private val tvReleaseDate: TextView? = view.findViewById(R.id.tvReleaseDate)
    private val fiveStarsComponent: FiveStarsComponent? =
      view.findViewById(R.id.fiveStarsComponent)

    fun bind(data: MovieData) {
      ivPoster?.setImagePath(data.posterPath)
      tvTitle?.text = data.movieTitle
      tvReleaseDate?.text = data.releaseDate
      fiveStarsComponent?.setVotesAvg(data.votes)
      view.setOnClickListener {
        (view.context as? Activity)?.let { activity ->
          ivPoster?.let {
            MovieDetailedActivity.startActivity(activity, data.movieReference, ivPoster)
          }
        }
      }
    }
  }

  companion object {
    const val VIEW_TYPE = -123
  }
}

fun List<Movie>.mapToElements(): List<MovieData> {
  return this.map { movie ->
    MovieData(
      posterPath = movie.posterPath,
      movieTitle = movie.title,
      releaseDate = movie.releaseDate,
      votes = movie.votesAverage,
      movieReference = movie
    )
  }
}

package info.firozansari.movieapp.presentation.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import info.firozansari.movieapp.R
import info.firozansari.movieapp.databinding.IntroPagerItemBinding

class IntroPagerAdapter : RecyclerView.Adapter<IntroPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        IntroPagerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = 3

    class ViewHolder(
        private val binding: IntroPagerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) = binding.apply {
            when (position) {
                0 -> updateIntro(
                    title = "Trailers & info of millions of movies, Tv shows",
                    description = "Get information about any movie/show at no price.",
                    imageDrawable = R.drawable.splash_big
                )
                1 -> updateIntro(
                    title = "Search and discover Movies, TV shows",
                    description = "Search or discover movies/shows by genres, get cast, find what's trendy from millions of data",
                    imageDrawable = R.drawable.splash_small
                )
                2 -> updateIntro(
                    title = "Liked something... want to watch it later ?",
                    description = "Create your own watchlist, favourite list, or rate any movie/show",
                    imageDrawable = R.drawable.splash_small
                )
            }
        }

        private fun updateIntro(
            title: String,
            description: String,
            imageDrawable: Int
        ) = binding.apply {
            introTitle.text = title
            introDescription.text = description
            introImage.load(imageDrawable)
        }
    }
}

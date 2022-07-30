package info.firozansari.movieapp.data.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import info.firozansari.movieapp.databinding.PageStateItemBinding

class PagingStateAdapter(val retry: () -> Unit) :
    LoadStateAdapter<PagingStateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(
            PageStateItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class ViewHolder(private val binding: PageStateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) = binding.apply {
            retryButton.setOnClickListener { retry() }
            when (loadState) {
                LoadState.Loading -> {
                    progressBar.isGone = false
                    retryButton.isGone = true
                }
                is LoadState.Error -> {
                    progressBar.isGone = true
                    retryButton.isGone = false
                }
                else -> {}
            }
        }
    }
}

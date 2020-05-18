package info.firozansari.moviemvp.presentation.movieList

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import info.firozansari.moviemvp.R
import info.firozansari.moviemvp.domain.model.MovieListType
import info.firozansari.moviemvp.domain.model.convertToMovieListType
import info.firozansari.moviemvp.presentation.ViewModelResult
import info.firozansari.moviemvp.presentation.util.show
import kotlinx.android.synthetic.main.fragment_movies_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListFragment : Fragment(R.layout.fragment_movies_list) {

    private val viewModel: MovieListViewModel by viewModel()

    private val observer = Observer<ViewModelResult> { result ->
        swRefreshLayout.isRefreshing = false
        when (result) {
            is ViewModelResult.Error -> {
                tvError.show(true)
                progressBar.stop()
                swRefreshLayout.show(false)
            }
            is ViewModelResult.Loading -> {
                progressBar.start()
                tvError.show(false)
                swRefreshLayout.show(false)
            }
            is ViewModelResult.Success -> {
                rvList.adapterImplementation()?.setup(viewModel.movies())
                showTheRefreshLayout()
            }
            is ViewModelResult.Updated -> {
                rvList.adapterImplementation()?.update(viewModel.newPart())
                showTheRefreshLayout()
            }
        }
    }

    private fun showTheRefreshLayout() {
        swRefreshLayout.show(true)
        progressBar.stop()
        tvError.show(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMovieTypeFromIntent()?.let { type ->
            setupViewModel(type)
            setupRecyclerView(type)
        }
    }

    private fun getMovieTypeFromIntent(): MovieListType? {
        return arguments?.getInt(MOVIE_TYPE_VALUE)?.convertToMovieListType()
    }

    private fun setupViewModel(type: MovieListType) {
        viewModel.onMoviesListReceived.observe(viewLifecycleOwner, observer)
        viewModel.setup(type)
    }

    private fun setupRecyclerView(type: MovieListType) {
        if (type == MovieListType.TopRated || type == MovieListType.Popular) {
            rvList.paginationSupport {
                viewModel.requestMore()
            }
        }
        swRefreshLayout.setOnRefreshListener {
            viewModel.reload()
        }
    }

    fun scrollToTop() {
        rvList.scrollToTop()
    }

    companion object {

        private const val MOVIE_TYPE_VALUE = "aspd"

        fun newInstance(type: MovieListType): MovieListFragment {
            return MovieListFragment().apply {
                arguments = Bundle().also { bundle ->
                    bundle.putInt(MOVIE_TYPE_VALUE, type.value)
                }
            }
        }

    }
}
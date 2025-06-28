package info.firozansari.movieapp.presentation.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import info.firozansari.movieapp.R
import info.firozansari.movieapp.databinding.FragmentMovieListBinding
import info.firozansari.movieapp.domain.responses.MovieDetailResponse
import info.firozansari.movieapp.domain.responses.VideoResult
import info.firozansari.movieapp.presentation.Config.TRAILER
import info.firozansari.movieapp.presentation.Config.YOUTUBE
import info.firozansari.movieapp.presentation.util.ErrorType
import info.firozansari.movieapp.presentation.util.Resource
import info.firozansari.movieapp.presentation.util.showSnackBar
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private val args: MovieDetailFragmentArgs by navArgs()
    private var _currentMovie: MovieDetailResponse? = null

    @Inject
    lateinit var movieDetailViewModelFactory: MovieDetailViewModel.MovieDetailViewModelFactory

    private val viewModel: MovieDetailViewModel by viewModels {
        MovieDetailViewModel.providesFactory(
            assistedFactory = movieDetailViewModelFactory,
            movieId = args.movieId
        )
    }

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.toolbar.title = "Movie Detail"
        binding.toolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }
        viewModel.movieDetail.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> binding.apply {
                    val message = it.message ?: "Something went wrong"
                    showSnackBar(message)
                    binding.progressBar.isGone = true
                    if (it.errorType == ErrorType.NETWORK) {
                        errorLayout.statusTextTitle.text = getString(R.string.connection_error)
                        errorLayout.statusTextDesc.text = getString(R.string.please_check_your_internet_connection)
                    } else {
                        errorLayout.statusTextTitle.text = "" //getString(R.string.something_went_wrong)
                        errorLayout.statusTextDesc.text = it.message
                    }
                }
                is Resource.Loading -> binding.apply {
                    binding.progressBar.isVisible = true
                }
                is Resource.Success -> binding.apply {
                    // See PlayerFragment for more details

                    _currentMovie = it.data
                    if (_currentMovie != null) {
                        binding.progressBar.isGone = true
                        binding.errorLayout.root.isGone = true
                        //binding.movieName.text = _currentMovie?.title
                        binding.toolbar.title = _currentMovie?.title
                        val totalVideos = it.data?.videos?.videosList as ArrayList
                        val trailers: List<VideoResult> = totalVideos.filter { toFilter ->
                            toFilter.type == TRAILER && toFilter.site == YOUTUBE
                        }
                        if (totalVideos.isEmpty()) {
                            // No video to play, not initialing youtube player
                        } else {
                            val trailer = if (trailers.isEmpty()) totalVideos[0] else trailers[0]
                            // TODO use Youtube Player
                        }
                    }
                }
            }
        }

        binding.errorLayout.retryButton.setOnClickListener { viewModel.getMovieDetail(args.movieId.toInt()) }
    }
}

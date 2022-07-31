package info.firozansari.movieapp.presentation.tvlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import info.firozansari.movieapp.R
import info.firozansari.movieapp.data.paging.PagingStateAdapter
import info.firozansari.movieapp.databinding.FragmentTvListBinding
import info.firozansari.movieapp.presentation.Config
import info.firozansari.movieapp.presentation.home.MediaListPagerAdapter
import info.firozansari.movieapp.presentation.util.ErrorType
import info.firozansari.movieapp.presentation.util.handleExceptions
import info.firozansari.movieapp.presentation.util.safeFragmentNavigation
import javax.inject.Inject

@AndroidEntryPoint
class TvListFragment : Fragment() {

  private var _binding: FragmentTvListBinding? = null
  private val binding get() = _binding!!
  private lateinit var adapter: MediaListPagerAdapter
  private val args: TvListFragmentArgs by navArgs()

  @Inject
  lateinit var tvListViewModelFactory: TvListViewModel.TrendingViewModelFactory

  private val viewModel: TvListViewModel by viewModels {
    TvListViewModel.providesFactory(
      assistedFactory = tvListViewModelFactory,
      mediaCategory = args.mediaCategory
    )
  }

  private lateinit var navController: NavController

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentTvListBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    navController = findNavController()
    binding.toolbar.title = args.mediaCategory
    setUpRecyclerViewAndNav()

    viewModel.categoryWiseMediaList.observe(viewLifecycleOwner) {
      adapter.submitData(lifecycle, it)
    }

    binding.errorLayout.retryButton.setOnClickListener { adapter.retry() }
  }

  private fun setUpRecyclerViewAndNav() {
    binding.toolbar.setNavigationOnClickListener {
      navController.popBackStack()
    }

    adapter = MediaListPagerAdapter(
      onPosterClick = if (args.mediaCategory != Config.BOLLYWOOD_MOVIES) {
        {
          // callback of Poster click
          parentFragmentManager.setFragmentResult(
            Config.MEDIA_SEND_REQUEST_KEY,
            bundleOf(
              Config.GENRES_ID_LIST_KEY to it.genreIds,
              Config.MEDIA_TITLE_KEY to (it.title ?: it.tvShowName),
              Config.IS_IT_A_MOVIE_KEY to !it.title.isNullOrEmpty(),
              Config.MEDIA_OVERVIEW_KEY to it.overview,
              Config.MEDIA_IMAGE_KEY to it.backdropPath,
              Config.MEDIA_YEAR_KEY to (it.releaseDate ?: it.tvShowFirstAirDate),
              Config.MEDIA_ID_KEY to it.id,
              Config.MEDIA_RATING_KEY to String.format("%.1f", it.voteAverage)
            )
          )

          safeFragmentNavigation(
            navController = navController,
            currentFragmentId = R.id.movieListFragment,
            actionId = R.id.action_movieListFragment_to_detailFragment
          )
        }
      } else {
        // BollyWood item click
        {
          parentFragmentManager.setFragmentResult(
            Config.MEDIA_PLAY_REQUEST_KEY,
            bundleOf(
              Config.MEDIA_ID_KEY to it.id,
              Config.IS_IT_A_MOVIE_KEY to true
            )
          )
          safeFragmentNavigation(
            navController = navController,
            currentFragmentId = R.id.movieListFragment,
            actionId = R.id.action_movieListFragment_to_playerFragment
          )
        }
      },
    )

    binding.listRecyclerview.adapter = adapter.withLoadStateHeaderAndFooter(
      footer = PagingStateAdapter { adapter.retry() },
      header = PagingStateAdapter { adapter.retry() }
    )
    binding.listRecyclerview.setHasFixedSize(true)

    adapter.addLoadStateListener {
      when (it.refresh) {
        is LoadState.NotLoading -> binding.apply {
          progressBar.isGone = true
          listRecyclerview.isGone = false
        }

        LoadState.Loading -> binding.apply {
          errorLayout.root.isGone = true
          progressBar.isGone = false
          listRecyclerview.isGone = true
        }

        is LoadState.Error -> binding.apply {
          progressBar.isGone = true
          errorLayout.root.isGone = false
          listRecyclerview.isGone = true
          val errorType: ErrorType =
            handleExceptions((it.refresh as LoadState.Error).error)
          if (errorType == ErrorType.NETWORK) {
            // Network problem
            errorLayout.statusTextTitle.text = "Connection Error"
            errorLayout.statusTextDesc.text = "Please check your internet connection"
          } else {
            // Http error or unknown
            errorLayout.statusTextTitle.text = "Oops.. Something went wrong"
            errorLayout.statusTextDesc.text = "Please try again"
          }
        }
      }
    }
  }
}

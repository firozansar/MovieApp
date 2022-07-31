package info.firozansari.movieapp.presentation.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import info.firozansari.movieapp.presentation.player.adapters.SeasonPickerAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import info.firozansari.movieapp.R
import info.firozansari.movieapp.databinding.SeasonPickerDialogBinding
import info.firozansari.movieapp.domain.responses.Season
import info.firozansari.movieapp.presentation.Config.CURRENT_SEASON_POSITION
import info.firozansari.movieapp.presentation.Config.SEASONS_LIST_REQUEST_KEY
import info.firozansari.movieapp.presentation.Config.SEASON_LIST
import info.firozansari.movieapp.presentation.Config.SEASON_NUMBER
import info.firozansari.movieapp.presentation.Config.SELECTED_SEASON_POSITION

class SeasonPickerDialogFragment : BottomSheetDialogFragment() {

    private var _binding: SeasonPickerDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SeasonPickerAdapter

    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SeasonPickerDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
        }
        parentFragmentManager.setFragmentResultListener(
            SEASONS_LIST_REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->

            adapter =
                SeasonPickerAdapter(bundle.getInt(CURRENT_SEASON_POSITION)) { seasonNumber: Int ->
                    // Callback of season options click
                    parentFragmentManager.setFragmentResult(
                        SELECTED_SEASON_POSITION,
                        bundleOf(SEASON_NUMBER to seasonNumber)
                    )
                    findNavController().navigateUp()
                }
            setUpRecyclerView()
            adapter.submitList(bundle.getParcelableArrayList<Season>(SEASON_LIST))
        }

        binding.closeIcon.setOnClickListener { dismiss() }
    }

    private fun setUpRecyclerView() {
        binding.rvSeasonsOptionsList.setHasFixedSize(true)
        binding.rvSeasonsOptionsList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

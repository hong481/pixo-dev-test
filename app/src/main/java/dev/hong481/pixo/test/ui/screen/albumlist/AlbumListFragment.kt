package dev.hong481.pixo.test.ui.screen.albumlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.hong481.pixo.test.R
import dev.hong481.pixo.test.databinding.FragmentAlbumListBinding
import dev.hong481.pixo.test.ui.screen.MainViewModel
import dev.hong481.pixo.test.util.base.livedata.EventObserver

@AndroidEntryPoint
class AlbumListFragment : Fragment() {

    companion object {
        const val TAG = "AlbumListFragment"
    }

    private lateinit var binding: FragmentAlbumListBinding

    private val mainViewModel: MainViewModel by viewModels()
    private val viewModel: AlbumListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initViewModel()
    }

    private fun initBinding() {
        binding.mainViewModel = mainViewModel
        binding.viewModel = viewModel
    }

    private fun initViewModel() {
        mainViewModel.eventLiveData.observe(viewLifecycleOwner, EventObserver {
            handleMainEvent(it)
        })
    }

    /**
     * MainViewModel Event 핸들링.
     */
    private fun handleMainEvent(event: MainViewModel.ViewEvent) = when (event) {
        is MainViewModel.ViewEvent.ActionMoveToPhotoPicker -> {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_albumListFragment_to_photoPickerFragment)
        }
        else -> Unit
    }
}
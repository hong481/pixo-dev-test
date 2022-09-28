package dev.hong481.pixo.test.ui.screen.albumlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.hong481.pixo.test.R
import dev.hong481.pixo.test.databinding.FragmentAlbumListBinding
import dev.hong481.pixo.test.ui.base.fragment.BaseFragment
import dev.hong481.pixo.test.ui.screen.MainViewModel
import dev.hong481.pixo.test.util.base.livedata.EventObserver

@AndroidEntryPoint
class AlbumListFragment : BaseFragment<FragmentAlbumListBinding>() {

    companion object {
        const val TAG = "AlbumListFragment"
    }

    private val mainViewModel: MainViewModel by viewModels()

    private val viewModel: AlbumListViewModel by viewModels()

    override fun getLayoutRes(): Int = R.layout.fragment_album_list

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        super.initBinding(inflater, container)
        binding.mainViewModel = mainViewModel
        binding.viewModel = viewModel
    }

    override fun initView() {
        // todo
    }

    override fun initViewModel() {
        mainViewModel.eventLiveData.observe(viewLifecycleOwner, EventObserver {
            handleMainViewEvent(it)
        })
    }

    /**
     * MainViewModel Event 핸들링.
     */
    private fun handleMainViewEvent(event: MainViewModel.ViewEvent) = when (event) {
        is MainViewModel.ViewEvent.ActionMoveToPhotoPicker -> {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_albumListFragment_to_photoPickerFragment)
        }
        else -> Unit
    }
}
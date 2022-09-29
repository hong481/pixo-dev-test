package dev.hong481.pixo.test.ui.screen.albumlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.hong481.pixo.test.R
import dev.hong481.pixo.test.databinding.FragmentAlbumListBinding
import dev.hong481.pixo.test.ui.base.fragment.BaseFragment
import dev.hong481.pixo.test.ui.screen.MainViewModel
import dev.hong481.pixo.test.ui.view.decoration.SpacesItemDecoration
import dev.hong481.pixo.test.util.base.extension.lifecycleContext
import dev.hong481.pixo.test.util.base.livedata.EventObserver

@AndroidEntryPoint
class AlbumListFragment : BaseFragment<FragmentAlbumListBinding>() {

    companion object {
        const val TAG = "AlbumListFragment"

        const val RECYCLE_VIEW_SPAN_COUNT = 2
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
        binding.rvAlbum.run {
            this.layoutManager =
                GridLayoutManager(this@AlbumListFragment.lifecycleContext, RECYCLE_VIEW_SPAN_COUNT)
            this.adapter = AlbumListAdapter(
                this@AlbumListFragment.lifecycleContext,
                this@AlbumListFragment,
                viewModel
            )
            this.addItemDecoration(
                SpacesItemDecoration(
                    spanCount = RECYCLE_VIEW_SPAN_COUNT,
                    spacing = context.resources.getDimensionPixelSize(R.dimen.album_list_padding)
                )
            )
        }
    }

    override fun initViewModel() {
        mainViewModel.eventLiveData.observe(viewLifecycleOwner, EventObserver {
            handleMainViewEvent(it)
        })
        viewModel.eventLiveData.observe(viewLifecycleOwner, EventObserver {
            handleMainViewEvent(it)
        })
    }

    /**
     * MainViewModel Event 핸들링.
     */
    private fun handleMainViewEvent(event: MainViewModel.ViewEvent) {
        when (event) {
            is MainViewModel.ViewEvent.ActionMoveToPhotoPicker -> {
                val selectedAlbum = event.selectedAlbum ?: return
                val action =
                    AlbumListFragmentDirections.actionAlbumListFragmentToPhotoPickerFragment(
                        selectedAlbum
                    )
                navController.navigate(action)
            }
            else -> {}
        }
    }
}
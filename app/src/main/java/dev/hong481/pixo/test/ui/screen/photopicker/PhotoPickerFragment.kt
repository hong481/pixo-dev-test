package dev.hong481.pixo.test.ui.screen.photopicker

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.hong481.pixo.test.R
import dev.hong481.pixo.test.databinding.FragmentPhotoPickerBinding
import dev.hong481.pixo.test.ui.base.fragment.BaseFragment
import dev.hong481.pixo.test.ui.screen.MainViewModel
import dev.hong481.pixo.test.ui.view.decoration.GridSpacesItemDecoration
import dev.hong481.pixo.test.util.base.extension.lifecycleContext
import dev.hong481.pixo.test.util.base.livedata.EventObserver


@AndroidEntryPoint
class PhotoPickerFragment : BaseFragment<FragmentPhotoPickerBinding>() {

    companion object {
        const val TAG = "PhotoPickerFragment"

        const val RECYCLE_VIEW_SPAN_COUNT = 3
    }

    private val mainViewModel: MainViewModel by viewModels()
    private val viewModel: PhotoPickerViewModel by viewModels()

    private val args: PhotoPickerFragmentArgs by navArgs()

    override fun getLayoutRes(): Int = R.layout.fragment_photo_picker

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        super.initBinding(inflater, container)
        binding.mainViewModel = mainViewModel
        binding.viewModel = viewModel

        initPhotoList()
    }

    override fun initView() {
        binding.rvAlbumPhoto.run {
            this.layoutManager =
                GridLayoutManager(
                    this@PhotoPickerFragment.lifecycleContext,
                    RECYCLE_VIEW_SPAN_COUNT
                )
            this.adapter = PhotoListAdapter(
                this@PhotoPickerFragment.lifecycleContext,
                this@PhotoPickerFragment,
                viewModel
            )
            this.addItemDecoration(
                GridSpacesItemDecoration(
                    spanCount = RECYCLE_VIEW_SPAN_COUNT,
                    spacing = context.resources.getDimensionPixelSize(R.dimen.photo_list_padding),
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

    private fun initPhotoList() {
        try {
            val album = args.album ?: return
            viewModel.fetchPhotoList(album)
        } catch (e: Exception) {
            Log.d(TAG, "initPhotoList. error: ${e.printStackTrace()}")
        }
    }

    /**
     * MainViewModel Event 핸들링.
     */
    private fun handleMainViewEvent(event: MainViewModel.ViewEvent) = when (event) {
        is MainViewModel.ViewEvent.ActionMoveToPhotoEditor -> {
            val action = PhotoPickerFragmentDirections.actionPhotoPickerFragmentToEditPhotoFragment(
                args.album,
                event.selectedPhoto
            )
            navController.navigate(action)
        }
        is MainViewModel.ViewEvent.ActionMoveToAlbumList -> {
            navController.navigate(R.id.action_photoPickerFragment_to_albumListFragment)
        }
        else -> Unit
    }
}
package dev.hong481.pixo.test.ui.screen.editphoto

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dev.hong481.pixo.test.R
import dev.hong481.pixo.test.databinding.FragmentEditPhotoBinding
import dev.hong481.pixo.test.ui.base.fragment.BaseFragment
import dev.hong481.pixo.test.ui.screen.MainViewModel
import dev.hong481.pixo.test.ui.view.decoration.GridSpacesItemDecoration
import dev.hong481.pixo.test.ui.view.decoration.HorizontalSpaceItemDecoration
import dev.hong481.pixo.test.util.SVGUtil
import dev.hong481.pixo.test.util.base.extension.lifecycleContext
import dev.hong481.pixo.test.util.base.livedata.EventObserver
import javax.inject.Inject


@AndroidEntryPoint
class EditPhotoFragment : BaseFragment<FragmentEditPhotoBinding>() {

    companion object {
        const val TAG = "EditPhotoFragment"
    }

    private val mainViewModel: MainViewModel by viewModels()
    private val viewModel: EditPhotoViewModel by viewModels()

    private val args: EditPhotoFragmentArgs by navArgs()

    @Inject
    lateinit var svgUtil: SVGUtil

    override fun getLayoutRes(): Int = R.layout.fragment_edit_photo

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        super.initBinding(inflater, container)
        binding.mainViewModel = mainViewModel
        binding.viewModel = viewModel
    }

    override fun initView() {
        val photo = args.photo
        binding.vCanvas.init(Uri.parse(photo.uriString))


        binding.rvSticker.run {
            this.adapter = StickerListAdapter(
                this@EditPhotoFragment.lifecycleContext,
                this@EditPhotoFragment,
                viewModel,
                svgUtil
            )
            this.addItemDecoration(
                HorizontalSpaceItemDecoration(
                    spacing = context.resources.getDimensionPixelSize(R.dimen.sticker_list_padding)
                )
            )
            this.setHasFixedSize(true)
        }
    }

    override fun initViewModel() {

        viewModel.getSvgStickerList()

        mainViewModel.eventLiveData.observe(viewLifecycleOwner, EventObserver {
            handleMainViewEvent(it)
        })
    }

    /**
     * MainViewModel Event 핸들링.
     */
    private fun handleMainViewEvent(event: MainViewModel.ViewEvent) = when (event) {
        is MainViewModel.ViewEvent.ActionMoveToAlbumList -> {
            navController.navigate(R.id.action_editPhotoFragment_to_photoPickerFragment)
        }
        is MainViewModel.ViewEvent.ActionMoveToPhotoPicker -> {
            val action = EditPhotoFragmentDirections.actionEditPhotoFragmentToPhotoPickerFragment(
                args.album
            )
            navController.navigate(action)
        }
        else -> Unit
    }


}
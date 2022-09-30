package dev.hong481.pixo.test.ui.screen.editphoto

import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.caverock.androidsvg.SVG
import dagger.hilt.android.AndroidEntryPoint
import dev.hong481.pixo.test.R
import dev.hong481.pixo.test.data.model.Sticker
import dev.hong481.pixo.test.databinding.FragmentEditPhotoBinding
import dev.hong481.pixo.test.ui.base.fragment.BaseFragment
import dev.hong481.pixo.test.ui.screen.MainViewModel
import dev.hong481.pixo.test.ui.view.decoration.HorizontalSpaceItemDecoration
import dev.hong481.pixo.test.util.SVGUtil
import dev.hong481.pixo.test.util.base.livedata.EventObserver
import javax.inject.Inject


@AndroidEntryPoint
class EditPhotoFragment : BaseFragment<FragmentEditPhotoBinding>() {

    companion object {
        const val TAG = "EditPhotoFragment"
    }

    private val mainViewModel: MainViewModel by activityViewModels()
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
                this@EditPhotoFragment,
                viewModel
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
        mainViewModel.eventLiveData.observe(viewLifecycleOwner, EventObserver {
            handleMainViewEvent(it)
            mainViewModel.setIsVisibleOverlayButton(false)
        })
        viewModel.selectStickerEvent.observe(viewLifecycleOwner, EventObserver { sticker ->
            canvasStickerOverlay(sticker) {
                mainViewModel.setIsVisibleOverlayButton(true)
            }
        })
    }

    /**
     * 캔버스에 스티커를 Overlay.
     */
    private fun canvasStickerOverlay(sticker: Sticker, done: (() -> Unit)) {
        val inputStream = sticker.path?.let { it ->
            assetManager?.open(it)
        } ?: return
        val svg = SVG.getFromInputStream(inputStream)
        val stickerBitmap = Bitmap.createBitmap(
            binding.vCanvas.width,
            binding.vCanvas.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(stickerBitmap)
        svg.renderToCanvas(canvas)

        binding.vCanvas.overlay(stickerBitmap, done)
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
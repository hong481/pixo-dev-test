package dev.hong481.pixo.test.ui.screen.editphoto

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.hong481.pixo.test.R
import dev.hong481.pixo.test.databinding.FragmentEditPhotoBinding
import dev.hong481.pixo.test.ui.base.fragment.BaseFragment
import dev.hong481.pixo.test.ui.screen.MainViewModel
import dev.hong481.pixo.test.util.base.livedata.EventObserver


@AndroidEntryPoint
class EditPhotoFragment : BaseFragment<FragmentEditPhotoBinding>() {

    companion object {
        const val TAG = "EditPhotoFragment"
    }

    private val mainViewModel: MainViewModel by viewModels()
    private val viewModel: EditPhotoViewModel by viewModels()


    override fun getLayoutRes(): Int = R.layout.fragment_edit_photo

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        super.initBinding(inflater, container)
        binding.mainViewModel = mainViewModel
        binding.viewModel = viewModel

        assetManager?.open("svg/001.svg")?.let {
            val bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            viewModel.testSVGRender(canvas, it)
            binding.vCanvas.setImageBitmap(bitmap)
        }
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
        is MainViewModel.ViewEvent.ActionMoveToAlbumList -> {
            navController.navigate(R.id.action_editPhotoFragment_to_photoPickerFragment)
        }
        is MainViewModel.ViewEvent.ActionMoveToPhotoPicker -> {
            navController.navigate(R.id.action_editPhotoFragment_to_photoPickerFragment)
        }
        else -> Unit
    }


}
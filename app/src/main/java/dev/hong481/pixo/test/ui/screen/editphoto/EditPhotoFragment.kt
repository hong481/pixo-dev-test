package dev.hong481.pixo.test.ui.screen.editphoto

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.hong481.pixo.test.R
import dev.hong481.pixo.test.databinding.FragmentEditPhotoBinding
import dev.hong481.pixo.test.ui.screen.MainViewModel
import dev.hong481.pixo.test.util.base.livedata.EventObserver


@AndroidEntryPoint
class EditPhotoFragment : Fragment() {

    companion object {
        const val TAG = "EditPhotoFragment"
    }

    private lateinit var binding: FragmentEditPhotoBinding

    private val mainViewModel: MainViewModel by viewModels()
    private val viewModel: EditPhotoViewModel by viewModels()

    private val assetManager: AssetManager? by lazy {
        context?.resources?.assets
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditPhotoBinding.inflate(inflater, container, false)
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

        assetManager?.open("svg/001.svg")?.let {
            val bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            viewModel.testSVGRender(canvas, it)
            binding.vCanvas.setImageBitmap(bitmap)
        }
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
        is MainViewModel.ViewEvent.ActionMoveToPhotoEditor -> {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_photoPickerFragment_to_editPhotoFragment)
        }
        else -> Unit
    }

}
package dev.hong481.pixo.test.ui.screen.photopicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.hong481.pixo.test.R
import dev.hong481.pixo.test.databinding.FragmentPhotoPickerBinding
import dev.hong481.pixo.test.ui.screen.MainViewModel
import dev.hong481.pixo.test.util.base.livedata.EventObserver


@AndroidEntryPoint
class PhotoPickerFragment : Fragment() {

    companion object {
        const val TAG = "PhotoPickerFragment"
    }

    private lateinit var binding: FragmentPhotoPickerBinding

    private val mainViewModel: MainViewModel by viewModels()
    private val viewModel: PhotoPickerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoPickerBinding.inflate(inflater, container, false)
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
        is MainViewModel.ViewEvent.ActionMoveToPhotoEditor -> {
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_photoPickerFragment_to_editPhotoFragment)
        }
        else -> Unit
    }
}
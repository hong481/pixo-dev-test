package dev.hong481.pixo.test.ui.screen.photopicker

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PhotoPickerViewModel @Inject constructor() : ViewModel() {

    companion object {
        const val TAG = "PhotoPickerViewModel"
    }

}
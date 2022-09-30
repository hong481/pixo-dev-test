package dev.hong481.pixo.test.ui.screen.editphoto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hong481.pixo.test.data.model.Sticker
import dev.hong481.pixo.test.data.repository.StickerRepository
import dev.hong481.pixo.test.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class EditPhotoViewModel @Inject constructor(
    private val svgStickerRepository: StickerRepository
) : BaseViewModel(), StickerViewHolder.ViewModel {

    companion object {
        const val TAG = "EditPhotoViewModel"
    }

    /**
     * 스티커 리스트.
     */
    val stickerList: LiveData<List<Sticker>> =
        MutableLiveData(svgStickerRepository.getStickerList())

    fun getSvgStickerList() {
        svgStickerRepository.getStickerList()
    }

    override fun selectSticker(sticker: Sticker) {
        // todo
    }
}
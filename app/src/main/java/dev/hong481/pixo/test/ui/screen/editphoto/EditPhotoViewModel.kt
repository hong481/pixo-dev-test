package dev.hong481.pixo.test.ui.screen.editphoto


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hong481.pixo.test.data.model.Sticker
import dev.hong481.pixo.test.data.repository.StickerRepository
import dev.hong481.pixo.test.ui.base.viewmodel.BaseViewModel
import dev.hong481.pixo.test.util.base.extension.notify
import dev.hong481.pixo.test.util.base.livedata.Event
import javax.inject.Inject


@HiltViewModel
class EditPhotoViewModel @Inject constructor(
    svgStickerRepository: StickerRepository
) : BaseViewModel(), StickerViewHolder.ViewModel {

    companion object {
        const val TAG = "EditPhotoViewModel"
    }

    /**
     * 스티커 리스트.
     */
    val stickerList: LiveData<List<Sticker>> =
        MutableLiveData(svgStickerRepository.getStickerList())

    /**
     * 스티커 선택 이벤트.
     */
    private val _selectStickerEvent = MutableLiveData<Event<Sticker>>()
    val selectStickerEvent: LiveData<Event<Sticker>> = _selectStickerEvent

    override fun selectSticker(sticker: Sticker) {
        _selectStickerEvent.notify = sticker
    }

}
package dev.hong481.pixo.test.ui.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hong481.pixo.test.ui.base.viewmodel.BaseViewModel
import dev.hong481.pixo.test.util.base.extension.notify
import dev.hong481.pixo.test.util.base.extension.postNotify
import dev.hong481.pixo.test.util.base.livedata.Event
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {

    companion object {
        const val TAG = "MainViewModel"
    }

    private val _eventLiveData = MutableLiveData<Event<ViewEvent>>()
    val eventLiveData: LiveData<Event<ViewEvent>>
        get() = _eventLiveData

    /**
     * 현재 NavController Destination.
     */
    private val _currentDestination = MutableLiveData<Int>()
    val currentDestination: LiveData<Int>
        get() = _currentDestination

    fun setCurrentDestination(resourceId: Int) {
        _currentDestination.value = resourceId
    }

    /**
     * View Event Notify.
     */
    private fun viewEvent(event: ViewEvent, isPost: Boolean = false) = if (isPost) {
        _eventLiveData.postNotify = event
    } else {
        _eventLiveData.notify = event
    }

    /**
     * 앰벌 리스트 Fragment 이동 이벤트.
     */
    fun actionMoveToAlbumListEvent() = viewEvent(ViewEvent.ActionMoveToAlbumList)

    /**
     * 사진 선택 Fragment 이동 이벤트.
     */
    fun actionMoveToPhotoPickerEvent() = viewEvent(ViewEvent.ActionMoveToPhotoPicker)

    /**
     * 사진 편집 Fragment 이동 이벤트.
     */
    fun actionMoveToPhotoEditorEvent() = viewEvent(ViewEvent.ActionMoveToPhotoEditor)

    sealed class ViewEvent {

        object ActionMoveToAlbumList : ViewEvent()

        object ActionMoveToPhotoPicker : ViewEvent()

        object ActionMoveToPhotoEditor : ViewEvent()
    }
}
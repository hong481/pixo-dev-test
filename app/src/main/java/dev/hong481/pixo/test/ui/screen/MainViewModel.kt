package dev.hong481.pixo.test.ui.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hong481.pixo.test.data.model.Album
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
     * 선택된 [Album]
     */
    private val _selectedAlbum = MutableLiveData<Album?>()
    val selectedAlbum: LiveData<Album?> = _selectedAlbum

    /**
     * PhotoAppbar Title Text
     */
    val photoAppBarTitleText: LiveData<String> = _selectedAlbum.map {
        it?.name ?: ""
    }

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
    fun actionMoveToPhotoPickerEvent(album: Album) = viewEvent(ViewEvent.ActionMoveToPhotoPicker(album))


    fun setSelectedAlbum(album: Album) {
        _selectedAlbum.value = album
    }

    sealed class ViewEvent {

        object ActionMoveToAlbumList : ViewEvent()

        data class ActionMoveToPhotoPicker(
            val selectedAlbum: Album
        ) : ViewEvent()

        data class ActionMoveToPhotoEditor(
            val selectedPhoto: Album.Photo
        ) : ViewEvent()
    }
}
package dev.hong481.pixo.test.ui.screen.photopicker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hong481.pixo.test.data.model.Album
import dev.hong481.pixo.test.data.repository.AlbumRepository
import dev.hong481.pixo.test.ui.screen.MainViewModel
import dev.hong481.pixo.test.util.base.extension.notify
import dev.hong481.pixo.test.util.base.livedata.Event
import javax.inject.Inject


@HiltViewModel
class PhotoPickerViewModel @Inject constructor(
    private val albumRepository: AlbumRepository,
) : ViewModel(), PhotoViewHolder.ViewModel {

    companion object {
        const val TAG = "PhotoPickerViewModel"
    }

    private val _eventLiveData: MutableLiveData<Event<MainViewModel.ViewEvent>> = MutableLiveData()
    val eventLiveData: LiveData<Event<MainViewModel.ViewEvent>>
        get() = _eventLiveData

    /**
     * 앨범 사진 리스트.
     */
    var photoList: LiveData<List<Album.Photo>> = MutableLiveData()

    private fun viewEvent(event: MainViewModel.ViewEvent) {
        _eventLiveData.notify = event
    }

    fun fetchPhotoList(album: Album) {
        photoList = albumRepository.getAlbumPhotoListFLow(
            album = album
        ).asLiveData()
    }

    override fun selectPhoto(photo: Album.Photo) {
        viewEvent(MainViewModel.ViewEvent.ActionMoveToPhotoEditor(photo))
    }
}
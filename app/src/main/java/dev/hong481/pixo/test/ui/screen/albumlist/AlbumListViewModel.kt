package dev.hong481.pixo.test.ui.screen.albumlist

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hong481.pixo.test.data.model.Album
import dev.hong481.pixo.test.data.repository.AlbumRepository
import dev.hong481.pixo.test.ui.base.viewmodel.BaseViewModel
import dev.hong481.pixo.test.ui.screen.MainViewModel
import dev.hong481.pixo.test.util.base.extension.notify
import dev.hong481.pixo.test.util.base.extension.refresh
import dev.hong481.pixo.test.util.base.livedata.Event
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AlbumListViewModel @Inject constructor(
    private val albumRepository: AlbumRepository,
) : BaseViewModel(), AlbumViewHolder.ViewModel {

    companion object {
        const val TAG = "AlbumListViewModel"
    }

    private val _eventLiveData: MutableLiveData<Event<MainViewModel.ViewEvent>> = MutableLiveData()
    val eventLiveData: LiveData<Event<MainViewModel.ViewEvent>>
        get() = _eventLiveData

    private fun viewEvent(event: MainViewModel.ViewEvent) {
        _eventLiveData.notify = event
    }

    /**
     * 앨범 리스트.
     */
    val albumList = MediatorLiveData<List<Album>>().apply {
        this.addSource(albumRepository.getAlbumListFlow().asLiveData()) {
            this.value = it
        }
    }

    fun refreshAlbumList() = viewModelScope.launch {
        albumList.value = albumRepository.getAlbumListFlow().last()
    }

    override fun selectElbum(album: Album) {
        viewEvent(MainViewModel.ViewEvent.ActionMoveToPhotoPicker(album))
    }

}
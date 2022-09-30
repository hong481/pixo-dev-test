package dev.hong481.pixo.test.data.repository

import dev.hong481.pixo.test.data.model.Album
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {

    companion object {
        const val TAG = "AlbumRepository"
    }

    fun getAlbumListFlow(): Flow<List<Album>>

    fun getAlbumPhotoListFLow(album: Album): Flow<List<Album.Photo>>
}
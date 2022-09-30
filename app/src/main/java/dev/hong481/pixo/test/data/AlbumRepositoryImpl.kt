package dev.hong481.pixo.test.data

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import dev.hong481.pixo.test.data.model.Album
import dev.hong481.pixo.test.data.repository.AlbumRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AlbumRepositoryImpl(
    private val context: Context
) : AlbumRepository {

    companion object {
        const val DESC = "DESC"
    }

    private val collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    override fun getAlbumListFlow(): Flow<List<Album>> = flow {
        val findAlbums = HashMap<Long, Album>()
        val projections = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DATE_ADDED,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.BUCKET_ID,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
        )
        val orderBy = "${MediaStore.Images.ImageColumns.DATE_ADDED} $DESC"

        context.contentResolver.query(
            collection,
            projections,
            null,
            null,
            orderBy
        )?.use { cursor ->

            val imageIdIndex =
                cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)
            val bucketIdIndex =
                cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_ID)
            val bucketNameIndex =
                cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME)

            if (cursor.moveToFirst()) {
                do {
                    val bucketId = cursor.getLong(bucketIdIndex)
                    val album = findAlbums[bucketId] ?: let {
                        val bucketName = cursor.getString(bucketNameIndex)
                        val uri = ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            cursor.getLong(imageIdIndex)
                        )
                        val album = Album(
                            id = bucketId,
                            name = bucketName,
                            uriString = uri.toString(),
                            count = 0
                        )
                        findAlbums[bucketId] = album
                        album
                    }
                    album.count++
                } while (cursor.moveToNext())
            }
        }
        emit(findAlbums.values.toList().sortedByDescending { it.count })
    }

    override fun getAlbumPhotoListFLow(album: Album): Flow<List<Album.Photo>> = flow {
        val findPhotos = HashMap<Long, Album.Photo>()
        val projection = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DATE_ADDED,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.BUCKET_ID
        )

        val selection = "${MediaStore.Images.Media.BUCKET_ID} LIKE ?"
        val selectionArgs: Array<String> = arrayOf(album.id.toString())

        context.contentResolver?.query(
            collection,
            projection,
            selection,
            selectionArgs,
            "${MediaStore.Images.ImageColumns.DATE_ADDED} $DESC"
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getLong(idColumn)
                    val uri = ContentUris.withAppendedId(collection, id)
                    findPhotos[id] = Album.Photo(
                        id = id,
                        uriString = uri.toString(),
                    )

                } while (cursor.moveToNext())
            }
        }
        emit(findPhotos.values.toList().sortedByDescending { it.id })
    }
}
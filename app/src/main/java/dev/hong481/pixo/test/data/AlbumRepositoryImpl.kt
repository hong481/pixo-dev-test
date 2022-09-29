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
        const val DEST = "DESC"
    }

    override fun getAlbumListFlow(): Flow<List<Album>> {
        return flow {
            val collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            val projections = arrayOf(
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.Images.ImageColumns.BUCKET_ID,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
            )
            val orderBy = "${MediaStore.Images.ImageColumns.DATE_TAKEN} $DEST"

            val findAlbums = HashMap<String, Album>()
            context.contentResolver.query(
                collection,
                projections,
                null,
                null,
                orderBy
            )?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val imageIdIndex =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)
                    val bucketIdIndex =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_ID)
                    val bucketNameIndex =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME)
                    do {
                        val bucketId = cursor.getString(bucketIdIndex)
                        val album = findAlbums[bucketId] ?: let {
                            val bucketName = cursor.getString(bucketNameIndex)
                            val uri = ContentUris.withAppendedId(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                cursor.getLong(imageIdIndex)
                            )
                            val album = Album(
                                id = bucketId,
                                name = bucketName,
                                uri = uri,
                                count = 1
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
    }
}
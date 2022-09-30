package dev.hong481.pixo.test.data.model

import dev.hong481.pixo.test.ui.base.recyclerview.BaseItemModel

/**
 * 앨범.
 */
data class Album(
    override val id: Long,

    val name: String,

    var count: Long = 0,

    var uriString: String? = null

) : BaseItemModel(id = id) {

    companion object {
        const val KEY_ALBUM = "ALBUM"
        const val KEY_PHOTO = "PHOTO"
    }

    /**
     *  사진.
     */
    class Photo(
        override val id: Long,

        var uriString: String? = null

    ) : BaseItemModel(id)

}

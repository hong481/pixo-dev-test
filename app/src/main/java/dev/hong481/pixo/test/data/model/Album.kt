package dev.hong481.pixo.test.data.model

import dev.hong481.pixo.test.ui.base.recyclerview.BaseItemModel

data class Album(
    override val id: String,

    val name: String,

    var count: Long = 0,

    var uriString: String? = null

) : BaseItemModel(id = id) {

    companion object {
        const val key = "selectedAlbum"
    }

}

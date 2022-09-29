package dev.hong481.pixo.test.data.model

import android.net.Uri
import dev.hong481.pixo.test.ui.base.recyclerview.BaseItemModel
import dev.hong481.pixo.test.ui.base.viewmodel.BaseViewModel
import java.io.File

data class Album(
    override val id: String,

    val name: String,

    var count: Long = 0,

    var uri: Uri? = null,

    var file: File? = null

) : BaseItemModel(id = id) {

    companion object {
        const val key = "selectedAlbum"
    }

}

package dev.hong481.pixo.test.data.model

import dev.hong481.pixo.test.ui.base.recyclerview.BaseItemModel

/**
 * SVG 스티커.
 */
data class Sticker(

    override val id: Long,

    var path: String? = null

) : BaseItemModel(id = id)

package dev.hong481.pixo.test.data

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import dev.hong481.pixo.test.data.model.Sticker
import dev.hong481.pixo.test.data.repository.StickerRepository

class StickerRepositoryImpl(
    private val context: Context
) : StickerRepository {

    companion object {
        const val SVG_PATH = "svg"
    }

    private val assetManager: AssetManager by lazy {
        context.assets
    }

    override fun getStickerList(): List<Sticker> {
        val findSticker: MutableList<Sticker> = mutableListOf()

        val pathList: List<String> = assetManager.list(SVG_PATH)?.toList() ?: listOf()

        for (index in pathList.indices) {
            val path = pathList[index]
            Log.d(StickerRepository.TAG, "getSvgStickerList. path:$path")
            findSticker.add(
                Sticker(
                    id = index.toLong(),
                    path = "$SVG_PATH/$path"
                )
            )
        }
        return findSticker
    }

}
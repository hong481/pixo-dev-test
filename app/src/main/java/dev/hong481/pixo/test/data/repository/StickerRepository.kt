package dev.hong481.pixo.test.data.repository

import dev.hong481.pixo.test.data.model.Sticker

interface StickerRepository {

    companion object {
        const val TAG = "SvgStickerRepository"
    }

    fun getStickerList(): List<Sticker>
}
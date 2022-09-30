package dev.hong481.pixo.test.ui.screen.editphoto

import android.content.Context
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.caverock.androidsvg.SVG
import dev.hong481.pixo.test.R
import dev.hong481.pixo.test.data.model.Sticker
import dev.hong481.pixo.test.databinding.ItemStickerBinding
import dev.hong481.pixo.test.ui.base.recyclerview.BaseRecyclerViewHolder
import dev.hong481.pixo.test.util.SVGUtil


class StickerViewHolder(


    private val context: Context,
    private val svgUtil: SVGUtil,
    viewGroup: ViewGroup,
    lifecycleOwner: LifecycleOwner,
    viewModel: ViewModel,


    ) : BaseRecyclerViewHolder<Sticker>(viewGroup, R.layout.item_sticker) {

    private val binding: ItemStickerBinding by lazy {
        ItemStickerBinding.bind(itemView)
    }

    init {
        binding.lifecycleOwner = lifecycleOwner
    }

    /**
     *  [SVG] 스티커 그리기.
     */
    fun drawSticker() {
        val path = item.value?.path ?: ""
        if (path.isEmpty()) {
            return
        }
        binding.ivSticker.apply {
            setSVG(
                SVG.getFromInputStream(assetManager.open(path))
            )
        }
    }

    interface ViewModel {

        /**
         * 스티커 선택.
         */
        fun selectSticker(sticker: Sticker)
    }
}
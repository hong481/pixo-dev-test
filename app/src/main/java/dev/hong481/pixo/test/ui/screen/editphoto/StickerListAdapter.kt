package dev.hong481.pixo.test.ui.screen.editphoto

import android.content.Context
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import dev.hong481.pixo.test.data.model.Sticker
import dev.hong481.pixo.test.ui.base.recyclerview.BaseRecyclerViewAdapter
import dev.hong481.pixo.test.ui.base.recyclerview.BaseRecyclerViewHolder
import dev.hong481.pixo.test.util.SVGUtil

class StickerListAdapter(

    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: StickerViewHolder.ViewModel,
    private val svgUtil: SVGUtil

) : BaseRecyclerViewAdapter<Sticker>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewHolder<Sticker> = StickerViewHolder(
        context,
        svgUtil,
        parent,
        lifecycleOwner,
        viewModel,
    )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val adapterPosition: Int = holder.adapterPosition.takeIf {
            it != RecyclerView.NO_POSITION
        } ?: return

        val sticker: Sticker = items[adapterPosition]

        when (holder) {
            is StickerViewHolder -> {
                holder.run {
                    this.onBind(sticker)
                    this.drawSticker()
                }
            }
        }
    }

}
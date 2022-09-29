package dev.hong481.pixo.test.ui.screen.albumlist

import android.content.Context
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import dev.hong481.pixo.test.data.model.Album
import dev.hong481.pixo.test.ui.base.recyclerview.BaseRecyclerViewAdapter
import dev.hong481.pixo.test.ui.base.recyclerview.BaseRecyclerViewHolder

class AlbumListAdapter(

    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: AlbumViewHolder.ViewModel

) : BaseRecyclerViewAdapter<Album>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewHolder<Album> = AlbumViewHolder(
        context,
        parent,
        lifecycleOwner,
        viewModel
    )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val adapterPosition: Int = holder.adapterPosition.takeIf {
            it != RecyclerView.NO_POSITION
        } ?: return

        val album: Album = items[adapterPosition]

        when (holder) {
            is AlbumViewHolder -> {
                holder.run {
                    this.onBind(album)
                    this.loadThumbnail()
                }
            }
        }
    }

}
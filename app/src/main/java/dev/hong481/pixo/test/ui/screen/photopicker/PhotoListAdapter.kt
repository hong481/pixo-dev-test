package dev.hong481.pixo.test.ui.screen.photopicker

import android.content.Context
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import dev.hong481.pixo.test.data.model.Album.Photo
import dev.hong481.pixo.test.ui.base.recyclerview.BaseRecyclerViewAdapter
import dev.hong481.pixo.test.ui.base.recyclerview.BaseRecyclerViewHolder

class PhotoListAdapter(

    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val viewModel: PhotoViewHolder.ViewModel

) : BaseRecyclerViewAdapter<Photo>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewHolder<Photo> = PhotoViewHolder(
        context,
        parent,
        lifecycleOwner,
        viewModel
    )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val adapterPosition: Int = holder.adapterPosition.takeIf {
            it != RecyclerView.NO_POSITION
        } ?: return

        val photo: Photo = items[adapterPosition]

        when (holder) {
            is PhotoViewHolder -> {
                holder.run {
                    this.onBind(photo)
                    this.loadThumbnail()
                }
            }
        }
    }

}
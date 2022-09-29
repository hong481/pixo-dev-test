package dev.hong481.pixo.test.ui.screen.albumlist

import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.bumptech.glide.Glide
import dev.hong481.pixo.test.R
import dev.hong481.pixo.test.data.model.Album
import dev.hong481.pixo.test.databinding.ItemAlbumBinding
import dev.hong481.pixo.test.ui.base.recyclerview.BaseRecyclerViewHolder

class AlbumViewHolder(

    private val context: Context,
    viewGroup: ViewGroup,
    lifecycleOwner: LifecycleOwner,
    viewModel: ViewModel,

    ) : BaseRecyclerViewHolder<Album>(viewGroup, R.layout.item_album) {

    private val binding: ItemAlbumBinding by lazy {
        ItemAlbumBinding.bind(itemView)
    }


    /**
     * 타이틀.
     */
    val titleText: LiveData<String> = item.map {
        it.name
    }

    /**
     * 이미지 개수.
     */
    val imageCountText: LiveData<String> = item.map {
        String.format(context.getString(R.string.image_count_format), it.count)
    }

    /**
     * 썸네일 로드.
     */
    fun loadThumbnail() {
        Glide.with(context)
            .load(Uri.parse(item.value?.uriString))
            .centerCrop()
            .into(binding.ivThumbnail)
    }

    init {
        binding.viewHolder = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = lifecycleOwner
    }

    interface ViewModel {

        /**
         * 앨범 선택.
         */
        fun selectElbum(album: Album)
    }
}
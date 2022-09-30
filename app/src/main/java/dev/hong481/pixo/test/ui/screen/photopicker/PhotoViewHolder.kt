package dev.hong481.pixo.test.ui.screen.photopicker

import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import dev.hong481.pixo.test.R
import dev.hong481.pixo.test.data.model.Album
import dev.hong481.pixo.test.databinding.ItemPhotoBinding
import dev.hong481.pixo.test.ui.base.recyclerview.BaseRecyclerViewHolder

class PhotoViewHolder(

    private val context: Context,
    viewGroup: ViewGroup,
    lifecycleOwner: LifecycleOwner,
    viewModel: ViewModel,

    ) : BaseRecyclerViewHolder<Album.Photo>(viewGroup, R.layout.item_photo) {

    private val binding: ItemPhotoBinding by lazy {
        ItemPhotoBinding.bind(itemView)
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
        fun selectPhoto(photo: Album.Photo)
    }
}
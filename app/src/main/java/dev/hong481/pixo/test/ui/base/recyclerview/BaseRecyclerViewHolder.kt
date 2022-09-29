package dev.hong481.pixo.test.ui.base.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import dev.hong481.pixo.test.util.base.extension.setValueIfNew

abstract class BaseRecyclerViewHolder<T>(

    itemView: View

) : RecyclerView.ViewHolder(itemView) {

    private val _item: MutableLiveData<T> = MutableLiveData()
    val item: LiveData<T> = _item

    constructor(parent: ViewGroup, layoutRes: Int) : this(
        LayoutInflater.from(parent.context).inflate(layoutRes, parent, false)
    )

    open fun onBind(item: T?) {
        _item setValueIfNew item
    }

}
package dev.hong481.pixo.test.ui.base.recyclerview

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    open var items: MutableList<T> = mutableListOf()
        set(value) {
            val diffCallback: BaseDiffUtilCallback<T> = BaseDiffUtilCallback(field, value)
            val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)

            field.clear()
            field.addAll(value)

            diffResult.dispatchUpdatesTo(this)
        }

    init {
        setHasStableIds(false)
    }

    override fun getItemCount(): Int = this.items.size

    final override fun setHasStableIds(hasStableIds: Boolean) =
        super.setHasStableIds(hasStableIds)

}
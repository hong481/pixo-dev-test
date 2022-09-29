package dev.hong481.pixo.test.ui.binding

import android.view.View
import androidx.databinding.BindingAdapter

object ViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("initVisibility")
    fun initVisibility(view: View, visibilityType: Int) {
        view.visibility = visibilityType
    }

}
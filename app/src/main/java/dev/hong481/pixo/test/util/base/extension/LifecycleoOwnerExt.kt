package dev.hong481.pixo.test.util.base.extension

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dev.hong481.pixo.test.ui.base.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Context를 반환.
 */
val LifecycleOwner.lifecycleContext: Context
    get() = when (this) {
        is Activity -> this
        is Fragment -> this.context
            ?: throw NullPointerException("The context of the fragment is null.")
        else -> throw NullPointerException("This method can only use Activity or Fragment.")
    }


/**
 * FragmentManager를 반환.
 */
val LifecycleOwner.lifecycleFragmentManager: FragmentManager
    get() = when (this) {
        is AppCompatActivity -> this.supportFragmentManager
        is Fragment -> this.childFragmentManager
        else -> throw NullPointerException("This method can only use Activity or Fragment.")
    }


/**
 * flow에 대한 Collect는 Activity가 Foground에 있을 때만 수행.
 */
fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) =
    lifecycleScope.launch {
        repeatOnLifecycle(
            Lifecycle.State.STARTED, block
        )
    }

/**
 *  BaseViewModel의 기본 Event 구독.
 */
fun LifecycleOwner.observeBaseViewModelEvent(
    viewModel: BaseViewModel,
    isShowToast: Boolean = true,
    isShowProgress: Boolean = true,
    isDismissProgress: Boolean = true
) = repeatOnStarted {
    viewModel.baseEventFlow.collect { event ->
        when (event) {
            is BaseViewModel.BaseEvent.ShowToast -> {
                if (isShowToast) Toast.makeText(
                    lifecycleContext, event.text, Toast.LENGTH_SHORT
                ).show()
            }
            is BaseViewModel.BaseEvent.ShowProgress -> {
                // todo Progress 활성화 로직.
            }
            is BaseViewModel.BaseEvent.DismissProgress -> {
                // todo Progress 비활성화 로직.
            }
        }
    }
}

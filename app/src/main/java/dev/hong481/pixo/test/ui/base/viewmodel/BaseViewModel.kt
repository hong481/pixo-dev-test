package dev.hong481.pixo.test.ui.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.hong481.pixo.test.util.base.flow.EventFlow
import dev.hong481.pixo.test.util.base.flow.MutableEventFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _baseEventFlow = MutableEventFlow<BaseEvent>()
    val baseEventFlow: EventFlow<BaseEvent> = _baseEventFlow

    open fun showToast(message: String?) {
        event(BaseEvent.ShowToast(message))
    }

    open fun showProgress() {
        event(BaseEvent.ShowProgress)
    }

    open fun dismissProgress() {
        event(BaseEvent.DismissProgress)
    }

    private fun event(event: BaseEvent) = viewModelScope.launch {
        _baseEventFlow.emit(event)
    }

    sealed class BaseEvent {
        data class ShowToast(val text: String?) : BaseEvent()
        object ShowProgress : BaseEvent()
        object DismissProgress : BaseEvent()
    }

}

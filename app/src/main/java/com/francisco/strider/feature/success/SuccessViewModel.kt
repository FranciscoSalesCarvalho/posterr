package com.francisco.strider.feature.success

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.francisco.strider.commons.viewModel.ChannelEventSenderImpl
import com.francisco.strider.commons.viewModel.EventSender
import javax.inject.Inject

class SuccessViewModel @Inject constructor() : ViewModel(),
    EventSender<SuccessViewModel.ScreenEvent> by ChannelEventSenderImpl() {

    fun onCloseClicked() = viewModelScope.sendEvent(ScreenEvent.Finish)

    sealed class ScreenEvent {
        object Finish : ScreenEvent()
    }
}
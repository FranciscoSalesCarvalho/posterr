package com.francisco.strider.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.francisco.strider.commons.viewModel.ChannelEventSenderImpl
import com.francisco.strider.commons.viewModel.EventSender
import com.francisco.strider.dsc.extensions.EMPTY_STRING
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel(),
    EventSender<MainViewModel.Navigation> by ChannelEventSenderImpl() {

    fun navigate(navigation: Navigation) {
        viewModelScope.sendEvent(navigation)
    }

    sealed class Navigation(
        val route: String = EMPTY_STRING,
        val popStack: Boolean = false
    ) {
        object HomeScreen : Navigation("home_screen")
    }
}

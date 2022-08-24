package com.francisco.strider.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.francisco.strider.commons.error.Error
import com.francisco.strider.commons.extensions.Result
import com.francisco.strider.commons.viewModel.ChannelEventSenderImpl
import com.francisco.strider.commons.viewModel.EventSender
import com.francisco.strider.data.entities.Post
import com.francisco.strider.domain.usecase.GetPostUseCase
import com.francisco.strider.feature.main.MainViewModel.Navigation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase
) : ViewModel(),
    EventSender<HomeViewModel.ScreenEvent> by ChannelEventSenderImpl() {

    val uiState = HomeScreenUiState()

    fun setup() {
        fetchPosts()
    }

    fun onNewPostClicked() {
        viewModelScope.sendEvent(ScreenEvent.NavigateTo(Navigation.PosterScreen))
    }

    fun onUserClicked() {
        viewModelScope.sendEvent(ScreenEvent.NavigateTo(Navigation.UserScreen))
    }

    fun onGoBackClicked() {
        viewModelScope.sendEvent(ScreenEvent.GoBack)
    }

    private fun fetchPosts() = viewModelScope.launch {
        uiState.screenState.value = ScreenState.Loading
        when (val result = getPostUseCase.execute()) {
            is Result.Failure ->
                uiState.screenState.value = ScreenState.Failure(result.error)
            is Result.Success ->
                uiState.screenState.value = ScreenState.Success(result.data)
        }
    }

    class HomeScreenUiState {
        val screenState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    }

    sealed class ScreenState {
        data class Success(val posts: List<Post>) : ScreenState()
        object Loading : ScreenState()
        data class Failure(val error: Error) : ScreenState()
    }

    sealed class ScreenEvent {
        data class NavigateTo(val navigation: Navigation) : ScreenEvent()
        object GoBack : ScreenEvent()
    }
}
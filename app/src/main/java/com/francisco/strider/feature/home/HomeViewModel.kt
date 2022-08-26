package com.francisco.strider.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.francisco.strider.commons.error.Error
import com.francisco.strider.commons.extensions.Result
import com.francisco.strider.commons.viewModel.ChannelEventSenderImpl
import com.francisco.strider.commons.viewModel.EventSender
import com.francisco.strider.domain.models.Post
import com.francisco.strider.domain.usecase.GetPostsUseCase
import com.francisco.strider.feature.main.MainViewModel.Navigation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val useCase: GetPostsUseCase
) : ViewModel(),
    EventSender<HomeViewModel.ScreenEvent> by ChannelEventSenderImpl() {

    val uiState = HomeScreenUiState()

    fun setup() {
        fetchPosts()
    }

    fun onGoBackClicked() {
        viewModelScope.sendEvent(ScreenEvent.GoBack)
    }

    fun onItemClicked(id: String) {
        viewModelScope.sendEvent(ScreenEvent.PostSelected(id = id))
        viewModelScope.sendEvent(ScreenEvent.NavigateTo(Navigation.DetailsScreen))
    }

    private fun fetchPosts() = viewModelScope.launch {
        uiState.screenState.value = ScreenState.Loading
        when (val result = useCase.execute()) {
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
        data class Success(val events: List<Post>) : ScreenState()
        object Loading : ScreenState()
        data class Failure(val error: Error) : ScreenState()
    }

    sealed class ScreenEvent {
        data class NavigateTo(val navigation: Navigation) : ScreenEvent()
        object GoBack : ScreenEvent()
        data class PostSelected(val id: String) : ScreenEvent()
    }
}
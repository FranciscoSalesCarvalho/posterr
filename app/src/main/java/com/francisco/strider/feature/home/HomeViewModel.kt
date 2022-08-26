package com.francisco.strider.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.francisco.strider.commons.viewModel.ChannelEventSenderImpl
import com.francisco.strider.commons.viewModel.EventSender
import com.francisco.strider.domain.models.Post
import com.francisco.strider.domain.repository.GithubRepository
import com.francisco.strider.feature.main.MainViewModel.Navigation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel(),
    EventSender<HomeViewModel.ScreenEvent> by ChannelEventSenderImpl() {

    val uiState = MutableStateFlow<Flow<PagingData<Post>>>(value = flowOf())

    fun setup() {
        fetchPosts()
    }

    fun onGoBackClicked() {
        viewModelScope.sendEvent(ScreenEvent.GoBack)
    }

    private fun fetchPosts() {
        uiState.value = repository.getRepositories().cachedIn(viewModelScope)
    }

    sealed class ScreenEvent {
        data class NavigateTo(val navigation: Navigation) : ScreenEvent()
        object GoBack : ScreenEvent()
    }
}
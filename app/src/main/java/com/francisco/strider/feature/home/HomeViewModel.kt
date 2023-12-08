package com.francisco.strider.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.francisco.strider.commons.viewModel.ChannelEventSenderImpl
import com.francisco.strider.commons.viewModel.EventSender
import com.francisco.strider.domain.models.Post
import com.francisco.strider.domain.repository.GithubRepository
import com.francisco.strider.feature.main.MainViewModel.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: GithubRepository
) : ViewModel(),
    EventSender<HomeViewModel.ScreenEvent> by ChannelEventSenderImpl() {

    var uiState by mutableStateOf<Flow<PagingData<Post>>>(value = flowOf())
        private set

    fun setup() {
        fetchPosts()
    }

    fun onGoBackClicked() {
        viewModelScope.sendEvent(ScreenEvent.GoBack)
    }

    private fun fetchPosts() {
        uiState = repository.getRepositories().cachedIn(viewModelScope)
    }

    sealed class ScreenEvent {
        data class NavigateTo(val navigation: Navigation) : ScreenEvent()
        object GoBack : ScreenEvent()
    }
}
package com.francisco.strider.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.francisco.strider.commons.error.Error
import com.francisco.strider.commons.extensions.Result
import com.francisco.strider.commons.viewModel.ChannelEventSenderImpl
import com.francisco.strider.commons.viewModel.EventSender
import com.francisco.strider.domain.models.Post
import com.francisco.strider.domain.usecase.CheckInUseCase
import com.francisco.strider.domain.usecase.GetPostUseCase
import com.francisco.strider.feature.main.MainViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val useCase: GetPostUseCase,
    private val checkInUseCase: CheckInUseCase
) : ViewModel(),
    EventSender<DetailsViewModel.ScreenEvent> by ChannelEventSenderImpl() {

    private var id: String = ""
    val uiState = DetailsScreenUiState()

    fun setup(id: String) {
        this.id = id
        fetchPost()
    }

    fun onGoBackClicked() {
        viewModelScope.sendEvent(ScreenEvent.GoBack)
    }

    fun fetchPost() = viewModelScope.launch {
        uiState.screenState.value = ScreenState.Loading
        when (val result = useCase.execute(id = id)) {
            is Result.Failure ->
                uiState.screenState.value = ScreenState.Failure(result.error)
            is Result.Success ->
                uiState.screenState.value = ScreenState.Success(result.data)
        }
    }

    fun checkIn() = viewModelScope.launch {
        uiState.isLoading.value = true
        when (val result = checkInUseCase.execute(id = id)) {
            is Result.Failure ->
                viewModelScope.sendEvent(ScreenEvent.Failure(error = result.error))
            is Result.Success ->
                viewModelScope.sendEvent(ScreenEvent.NavigateTo(MainViewModel.Navigation.SuccessScreen))
        }
        uiState.isLoading.value = false
    }

    class DetailsScreenUiState {
        val isLoading = MutableStateFlow(false)
        val screenState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    }

    sealed class ScreenState {
        data class Success(val event: Post) : ScreenState()
        object Loading : ScreenState()
        data class Failure(val error: Error) : ScreenState()
    }

    sealed class ScreenEvent {
        object GoBack : ScreenEvent()
        data class Failure(val error: Error) : ScreenEvent()
        data class NavigateTo(val navigation: MainViewModel.Navigation) : ScreenEvent()
    }
}
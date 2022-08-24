package com.francisco.strider.feature.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.francisco.strider.commons.error.Error
import com.francisco.strider.commons.extensions.Result
import com.francisco.strider.commons.viewModel.ChannelEventSenderImpl
import com.francisco.strider.commons.viewModel.EventSender
import com.francisco.strider.data.entities.User
import com.francisco.strider.domain.usecase.GetUserUseCase
import com.francisco.strider.domain.usecase.SaveUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel(),
    EventSender<UserViewModel.ScreenEvent> by ChannelEventSenderImpl() {

    val uiState = UserScreenUiState()

    fun setup() {
        dispatchSaveUser()
    }

    private fun dispatchSaveUser() {
        uiState.screenState.value = ScreenState.Loading
        viewModelScope.launch {
            when (val result = saveUserUseCase.execute(user = User.mock())) {
                is Result.Failure ->
                    uiState.screenState.value = ScreenState.Failure(result.error)
                is Result.Success -> fetchUserSaved()
            }
        }
    }

    private fun fetchUserSaved() = viewModelScope.launch {
        when (val result = getUserUseCase.execute(id = 1)) {
            is Result.Failure ->
                uiState.screenState.value = ScreenState.Failure(result.error)
            is Result.Success ->
                uiState.screenState.value = ScreenState.Success(user = result.data)
        }
    }

    fun onGoBackClicked() {
        viewModelScope.sendEvent(ScreenEvent.GoBack)
    }

    class UserScreenUiState {
        val screenState = MutableStateFlow<ScreenState>(ScreenState.Loading)
    }

    sealed class ScreenState {
        object Loading : ScreenState()
        data class Success(val user: User) : ScreenState()
        data class Failure(val error: Error) : ScreenState()
    }

    sealed class ScreenEvent {
        object GoBack : ScreenEvent()
    }
}
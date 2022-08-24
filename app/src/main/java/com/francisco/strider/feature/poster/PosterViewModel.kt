package com.francisco.strider.feature.poster

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.francisco.strider.commons.error.Error
import com.francisco.strider.commons.extensions.Result
import com.francisco.strider.commons.viewModel.ChannelEventSenderImpl
import com.francisco.strider.commons.viewModel.EventSender
import com.francisco.strider.domain.usecase.SavePostUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val MAXIMUM_CHARACTERS = 777

class PosterViewModel @Inject constructor(
    private val savePostUseCase: SavePostUseCase
) : ViewModel(),
    EventSender<PosterViewModel.ScreenEvent> by ChannelEventSenderImpl() {

    val uiState = FieldScreenUiState()

    fun onValueChanged(textField: TextFieldValue) {
        if (textField.text.length <= MAXIMUM_CHARACTERS) {
            uiState.textField.value = textField
            uiState.isTextFieldValid.value = true
        }
        uiState.missingCharacters.value = MAXIMUM_CHARACTERS - uiState.textField.value.text.length
    }

    fun onGoBackClicked() {
        viewModelScope.sendEvent(ScreenEvent.GoBack)
    }

    fun onContinueClicked() {
        val post = uiState.textField.value.text
        if (post.length in 1..MAXIMUM_CHARACTERS) {
            dispatchSavePost()
        } else {
            uiState.isTextFieldValid.value = false
        }
    }

    private fun dispatchSavePost() {
        uiState.isLoading.value = true
        viewModelScope.launch {
            when (val result = savePostUseCase.execute(post = uiState.textField.value.text)) {
                is Result.Failure -> sendEvent(ScreenEvent.SavePostError(result.error))
                is Result.Success -> onGoBackClicked()
            }
        }
        uiState.isLoading.value = false
    }

    class FieldScreenUiState {
        val isLoading = MutableStateFlow(value = false)
        val textField = MutableStateFlow(value = TextFieldValue())
        val isTextFieldValid = MutableStateFlow(value = true)
        val missingCharacters = MutableStateFlow(value = MAXIMUM_CHARACTERS)
    }

    sealed class ScreenEvent {
        object GoBack : ScreenEvent()
        data class SavePostError(val error: Error) : ScreenEvent()
    }
}
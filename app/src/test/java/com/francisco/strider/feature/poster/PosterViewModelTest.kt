package com.francisco.strider.feature.poster

import androidx.compose.ui.text.input.TextFieldValue
import com.francisco.strider.commons.extensions.Result
import com.francisco.strider.domain.usecase.SavePostUseCase
import com.francisco.strider.feature.home.CoroutinesTestRule
import com.francisco.strider.feature.poster.PosterViewModel.ScreenEvent
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PosterViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val testCoroutineScope = TestCoroutineScope(Job())

    private val savePostUseCase: SavePostUseCase = mockk(relaxed = true)

    private val observeEventMock: (ScreenEvent) -> (Unit) = mockk(relaxed = true)
    private val screenLoadingState: (Boolean) -> Unit = mockk(relaxed = true)

    private lateinit var viewModel: PosterViewModel

    @Before
    fun setup() {
        viewModel = PosterViewModel(savePostUseCase = savePostUseCase)
        prepareEventObserver()
    }

    @Test
    fun `when clicked onGoBackClicked then dispatch event GoBack`() {
        // when
        viewModel.onGoBackClicked()

        // then
        verify { observeEventMock(ScreenEvent.GoBack) }
    }

    @Test
    fun `when onValueChanged called, with valid name, must save text and enable button`() {
        //given
        val textFieldValue = TextFieldValue(text = POSTERR)

        //when
        viewModel.onValueChanged(textFieldValue)

        //then
        assertEquals(POSTERR, viewModel.uiState.textField.value.text)
        assertTrue(viewModel.uiState.isTextFieldValid.value)
    }

    @Test
    fun `when clicked onResultLauncherResult should fetch posts successful`() {
        val post = "my first post"
        coEvery {
            savePostUseCase.execute(post = post)
        } returns Result.Success(Unit)

        viewModel.onValueChanged(textField = TextFieldValue(text = post))
        viewModel.onContinueClicked()

        verifyOrder {
            screenLoadingState(true)
            observeEventMock.invoke(ScreenEvent.GoBack)
            screenLoadingState(false)
        }
    }

    private fun prepareEventObserver() = testCoroutineScope.run {
        launch { viewModel.eventsFlow.collect { observeEventMock(it) } }
        launch { viewModel.uiState.isLoading.collect { screenLoadingState(it) } }
    }

    private companion object {
        const val POSTERR = "Valid Post"
    }
}
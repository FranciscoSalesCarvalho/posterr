package com.francisco.strider.feature.home

import com.francisco.strider.commons.error.Error
import com.francisco.strider.commons.extensions.Result
import com.francisco.strider.data.entities.Post
import com.francisco.strider.domain.usecase.GetPostUseCase
import com.francisco.strider.feature.home.HomeViewModel.ScreenEvent
import com.francisco.strider.feature.home.HomeViewModel.ScreenState
import com.francisco.strider.feature.main.MainViewModel.Navigation.PosterScreen
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val testCoroutineScope = TestCoroutineScope(Job())

    private val getPostUseCase: GetPostUseCase = mockk(relaxed = true)

    private val observeEventMock: (ScreenEvent) -> (Unit) = mockk(relaxed = true)
    private val screenState: (ScreenState) -> Unit = mockk(relaxed = true)

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel(getPostUseCase = getPostUseCase)
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
    fun `when clicked onNewPostClicked then dispatch event Navigation to Poster`() {
        // when
        viewModel.onNewPostClicked()

        // then
        verify { observeEventMock(ScreenEvent.NavigateTo(PosterScreen)) }
    }

    @Test
    fun `when clicked onResultLauncherResult should fetch posts successful`() {
        val posts = listOf(Post.mock())
        coEvery {
            getPostUseCase.execute()
        } returns Result.Success(posts)

        viewModel.setup()

        verifyOrder {
            screenState(ScreenState.Loading)
            screenState(ScreenState.Success(posts = posts))
        }
    }

    @Test
    fun `when clicked onResultLauncherResult should fetch posts failure`() {
        val error: Error = mockk(relaxed = true)
        coEvery {
            getPostUseCase.execute()
        } returns Result.Failure(error = error)

        viewModel.setup()

        verifyOrder {
            screenState(ScreenState.Loading)
            screenState(ScreenState.Failure(error = error))
        }
    }

    private fun prepareEventObserver() = testCoroutineScope.run {
        launch { viewModel.eventsFlow.collect { observeEventMock(it) } }
        launch { viewModel.uiState.screenState.collect { screenState(it) } }
    }

}
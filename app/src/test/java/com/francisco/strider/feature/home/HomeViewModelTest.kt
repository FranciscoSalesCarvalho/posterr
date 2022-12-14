package com.francisco.strider.feature.home

import com.francisco.strider.CoroutinesTestRule
import com.francisco.strider.commons.error.Error
import com.francisco.strider.commons.extensions.Result
import com.francisco.strider.domain.models.Post
import com.francisco.strider.domain.usecase.GetPostsUseCase
import com.francisco.strider.feature.home.HomeViewModel.ScreenEvent
import com.francisco.strider.feature.main.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val testCoroutineScope = TestCoroutineScope(Job())

    private val useCase: GetPostsUseCase = mockk(relaxed = true)

    private val observeEventMock: (ScreenEvent) -> (Unit) = mockk(relaxed = true)
    private val screenState: (HomeViewModel.ScreenState) -> Unit = mockk(relaxed = true)

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel(useCase = useCase)
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
    fun `when clicked onItemClicked then dispatch event Navigate`() {
        // given
        val id = "1"

        // when
        viewModel.onItemClicked(id = id)

        // then
        verifyOrder {
            observeEventMock(ScreenEvent.PostSelected(id = id))
            observeEventMock(ScreenEvent.NavigateTo(navigation = MainViewModel.Navigation.DetailsScreen))
        }
    }

    @Test
    fun `when clicked onResultLauncherResult should fetch posts successful`() = runBlocking {
        val posts = listOf(Post.mock())
        coEvery {
            useCase.execute()
        } returns Result.Success(data = posts)

        viewModel.setup()

        verifyOrder {
            screenState(HomeViewModel.ScreenState.Loading)
            screenState(HomeViewModel.ScreenState.Success(events = posts))
        }
    }

    @Test
    fun `when clicked onResultLauncherResult should fetch posts failure`() = runBlocking {
        val error: Error = mockk(relaxed = true)
        coEvery {
            useCase.execute()
        } returns Result.Failure(error = error)

        viewModel.setup()

        verifyOrder {
            screenState(HomeViewModel.ScreenState.Loading)
            screenState(HomeViewModel.ScreenState.Failure(error = error))
        }
    }

    private fun prepareEventObserver() = testCoroutineScope.run {
        launch { viewModel.eventsFlow.collect { observeEventMock(it) } }
        launch { viewModel.uiState.screenState.collect { screenState(it) } }
    }

}
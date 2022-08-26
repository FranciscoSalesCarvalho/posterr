package com.francisco.strider.feature.details

import com.francisco.strider.CoroutinesTestRule
import com.francisco.strider.commons.error.Error
import com.francisco.strider.commons.extensions.Result
import com.francisco.strider.domain.models.Post
import com.francisco.strider.domain.usecase.CheckInUseCase
import com.francisco.strider.domain.usecase.GetPostUseCase
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
class DetailsViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val testCoroutineScope = TestCoroutineScope(Job())

    private val useCase: GetPostUseCase = mockk(relaxed = true)
    private val checkInUseCase: CheckInUseCase = mockk(relaxed = true)

    private val observeEventMock: (DetailsViewModel.ScreenEvent) -> (Unit) = mockk(relaxed = true)
    private val screenState: (DetailsViewModel.ScreenState) -> Unit = mockk(relaxed = true)

    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setup() {
        viewModel = DetailsViewModel(useCase = useCase, checkInUseCase = checkInUseCase)
        prepareEventObserver()
    }

    @Test
    fun `when clicked onGoBackClicked then dispatch event GoBack`() {
        // when
        viewModel.onGoBackClicked()

        // then
        verify { observeEventMock(DetailsViewModel.ScreenEvent.GoBack) }
    }

    @Test
    fun `when clicked onResultLauncherResult should fetch posts successful`() = runBlocking {
        val posts = Post.mock()
        coEvery {
            useCase.execute(id = ID)
        } returns Result.Success(data = posts)

        viewModel.setup(id = ID)

        verifyOrder {
            screenState(DetailsViewModel.ScreenState.Loading)
            screenState(DetailsViewModel.ScreenState.Success(event = posts))
        }
    }

    @Test
    fun `when clicked onResultLauncherResult should fetch posts failure`() = runBlocking {
        val error: Error = mockk(relaxed = true)
        coEvery {
            useCase.execute(id = ID)
        } returns Result.Failure(error = error)

        viewModel.setup(id = ID)

        verifyOrder {
            screenState(DetailsViewModel.ScreenState.Loading)
            screenState(DetailsViewModel.ScreenState.Failure(error = error))
        }
    }

    private fun prepareEventObserver() = testCoroutineScope.run {
        launch { viewModel.eventsFlow.collect { observeEventMock(it) } }
        launch { viewModel.uiState.screenState.collect { screenState(it) } }
    }

    companion object {
        const val ID = "1"
    }

}
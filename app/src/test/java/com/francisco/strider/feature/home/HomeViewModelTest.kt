package com.francisco.strider.feature.home

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.francisco.strider.CoroutinesTestRule
import com.francisco.strider.domain.models.Post
import com.francisco.strider.domain.repository.GithubRepository
import com.francisco.strider.feature.home.HomeViewModel.ScreenEvent
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val testCoroutineScope = TestCoroutineScope(Job())

    private val repository: GithubRepository = mockk(relaxed = true)

    private val observeEventMock: (ScreenEvent) -> (Unit) = mockk(relaxed = true)
    private val screenState: (Flow<PagingData<Post>>) -> Unit = mockk(relaxed = true)

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        viewModel = HomeViewModel(repository = repository)
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
    fun `when clicked onResultLauncherResult should fetch posts successful`() = runBlocking {
        val posts = listOf(Post.mock())
        val paging = PagingData.from(posts)
        val flow = flowOf(paging)
        coEvery {
            repository.getRepositories()
        } returns flow

        viewModel.setup()
    }

    private fun prepareEventObserver() = testCoroutineScope.run {
        launch { viewModel.eventsFlow.collect { observeEventMock(it) } }
        launch { viewModel.uiState.collect { screenState(it) } }
    }

}
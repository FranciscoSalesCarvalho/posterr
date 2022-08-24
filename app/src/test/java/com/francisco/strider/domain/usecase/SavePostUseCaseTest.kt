package com.francisco.strider.domain.usecase

import com.francisco.strider.commons.error.ErrorHandler
import com.francisco.strider.commons.extensions.Result
import com.francisco.strider.data.entities.Post
import com.francisco.strider.domain.repository.PostRepository
import com.francisco.strider.feature.home.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SavePostUseCaseTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val repository: PostRepository = mockk(relaxed = true)

    private lateinit var useCase: SavePostUseCase

    @Before
    fun setUp() {
        useCase = SavePostUseCase(repository = repository)
    }

    @Test
    fun `when execute usecase then request success`(): Unit = runBlocking {
        //given
        coEvery {
            repository.getPosts()
        } returns listOf()

        //when
        val result = useCase.execute(post = POST)

        //then
        coVerify(exactly = 1) {
            repository.getPosts()
        }

        assertTrue(result is Result.Success)
    }

    @Test
    fun `when execute usecase then request failure`(): Unit = runBlocking {
        //given
        coEvery {
            repository.getPosts()
        } returns listOf(Post.mock(), Post.mock(), Post.mock(), Post.mock(), Post.mock())

        //when
        val result = useCase.execute(post = POST)

        //then
        coVerify(exactly = 1) {
            repository.getPosts()
        }

        assertEquals("Post limit exceeded", (result as Result.Failure).error.message)
    }

    companion object {
        const val POST = "this is a post"
    }
}